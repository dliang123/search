package com.yt.ic.search.service;


import com.yt.ic.search.model.Index;
import com.yt.ic.search.model.IndexField;
import com.yt.ic.search.model.SearchMode;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索核心逻辑
 * Created by icewang on 2017-03-09.
 */
@Component
public class SearchCore {

    private String rootPath = "../lucene/";

    private Analyzer analyzer = new IKAnalyzer();

    private boolean debug = false;

    /**
     * 删除指定scope的全部索引
     *
     * @param scope
     * @throws IOException
     */
    public void deleteAllIndexes(String scope) throws IOException {
        Directory directory = getDirectory(scope);
        IndexWriter indexWriter = getIndexWriter(directory);
        indexWriter.deleteAll();
        indexWriter.commit();
        indexWriter.close();
        directory.close();
    }

    /**
     * 创建单个索引
     *
     * @param scope
     * @param index
     * @throws IOException
     */
    public void createIndex(String scope, Index index) throws IOException {
        if (StringUtils.isEmpty(scope) || index == null) {
            throw new IllegalArgumentException();
        }
        Directory directory = getDirectory(scope);
        IndexWriter indexWriter = getIndexWriter(directory);
        indexWriter.addDocument(toLuceneDocument(index));
        indexWriter.close();
        directory.close();
    }

    /**
     * 批量创建索引
     *
     * @param scope
     * @param indexes
     * @throws IOException
     */
    public void createIndexes(String scope, List<Index> indexes) throws IOException {
        if (StringUtils.isEmpty(scope) || indexes == null || indexes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Directory directory = getDirectory(scope);
        IndexWriter indexWriter = getIndexWriter(directory);
        for (Index index : indexes) {
            indexWriter.addDocument(toLuceneDocument(index));
        }
        indexWriter.close();
        directory.close();
    }

    /**
     * 查询索引
     *
     * @param scope
     * @param queryString
     * @param indexField
     * @param maxSize
     * @return
     * @throws IOException
     */
    public List<Index> getIndexes(String scope, String queryString, String indexField,
                                  SearchMode searchMode, int maxSize) throws IOException {
        if (StringUtils.isEmpty(scope) || StringUtils.isEmpty(queryString)
                || StringUtils.isEmpty(indexField)) {
            throw new IllegalArgumentException();
        }
        Directory directory = getDirectory(scope);
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        // 构造查询对象
        Query query = getQuery(queryString, indexField, searchMode);

        List<Index> result = new ArrayList<>();

        ScoreDoc[] hits = searcher.search(query, maxSize, new Sort()).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Explanation exp = searcher.explain(query, hits[i].doc);
            Document hitDoc = searcher.doc(hits[i].doc);
            Index index = toIndex(hitDoc, exp.getValue());
            result.add(index);
        }
        reader.close();
        directory.close();
        return result;
    }

    /**
     * 分词，解析器可通过Bean参数配置，默认使用IKAnalyzer
     *
     * @param query
     * @return
     */
    public List<String> splitWords(String query) {
        // 预处理，人为分词优先
        String[] preStr = query.split(" ");
        List<String> preWords = Arrays.asList(preStr).stream()
                .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toList());

        if (analyzer == null) { // 不使用解析器分词
            return preWords;
        }
        List<String> result = new ArrayList<>();
        for (String word : preWords) {
            StringReader reader = new StringReader(word);
            TokenStream tokenStream = null;
            try {
                tokenStream = analyzer.tokenStream("", reader);
                CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
                tokenStream.reset();
                while (tokenStream.incrementToken()) {
                    result.add(term.toString());
                }
                tokenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        }
        return result;
    }

    private Document toLuceneDocument(Index index) {
        Document document = new Document();
        for (IndexField indexField : index.getFields()) {
            document.add(toLuceneField(indexField));
        }
        return document;
    }

    private Field toLuceneField(IndexField indexField) {
        // 索引字段忽略大小写
        String value = indexField.isIndexed()
                ? indexField.getValue().toLowerCase()
                : indexField.getValue();
        return new Field(indexField.getKey(), value,
                toLuceneFieldType(indexField.isIndexed(), indexField.isAnalyzed()));
    }

    private FieldType toLuceneFieldType(boolean indexed, boolean tokenized) {
        FieldType fieldType = new FieldType();
        fieldType.setStored(true);
        fieldType.setIndexed(indexed);
        fieldType.setTokenized(tokenized);
        return fieldType;
    }

    private Index toIndex(Document document, float score) {
        List<IndexField> fields = new ArrayList<>();
        document.getFields().stream().filter(field -> field instanceof Field).forEach(field -> {
            IndexField indexField = toIndexField((Field) field);
            fields.add(indexField);
        });
        Index index = new Index();
        index.setFields(fields);
        index.setScore(score);
        return index;
    }

    private IndexField toIndexField(Field field) {
        return new IndexField(field.name(), field.stringValue(),
                field.fieldType().tokenized(), field.fieldType().indexed());
    }

    private IndexWriter getIndexWriter(Directory directory) throws IOException {
        IndexWriterConfig indexWriterConfig =
                new IndexWriterConfig(Version.LUCENE_4_10_4, new IKAnalyzer());
        indexWriterConfig.setMaxBufferedDocs(200);
        return new IndexWriter(directory, indexWriterConfig);
    }

    private Query getQuery(String queryString, final String indexField, SearchMode searchMode) throws IOException {
        queryString = queryString.toLowerCase();
        if (searchMode == SearchMode.NOT_ANALYZED) {
            return new WildcardQuery(new Term(indexField, "*" + queryString.replace(" ", "") + "*"));
        } else {
            List<String> words = splitWords(queryString);
            if (searchMode == SearchMode.ANALYZED_AND_ANY_IN && !words.contains(queryString)) {
                words.add(queryString);
            }
            BooleanQuery booleanQuery = new BooleanQuery();
            words.stream().forEach(word -> {
                Query q = new WildcardQuery(new Term(indexField, "*" + word + "*"));
                if (searchMode == SearchMode.ANALYZED_AND_ALL_IN) {
                    booleanQuery.add(q, BooleanClause.Occur.MUST);
                } else {
                    booleanQuery.add(q, BooleanClause.Occur.SHOULD);
                }
            });
            return booleanQuery;
        }
    }

    private Directory getDirectory(String scope) throws IOException {
        File file = new File(getIndexPath(scope));
        if (!file.exists()) {
            file.mkdir();
        }
        return FSDirectory.open(file);
    }

    private String getIndexPath(String scope) {
        if (StringUtils.isEmpty(scope)) {
            throw new IllegalArgumentException();
        }
        StringBuffer buffer = new StringBuffer();
        if (debug || StringUtils.isEmpty(rootPath)) {
            buffer.append("../lucene/");
        } else {
            buffer.append(rootPath.endsWith("/") ? rootPath : rootPath + "/");
        }
        buffer.append(scope);
        return buffer.toString();

    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
