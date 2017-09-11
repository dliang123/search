package com.yt.ic.search.service;


import com.yt.ic.search.api.SearchIndexService;
import com.yt.ic.search.model.Index;
import com.yt.ic.search.model.SearchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by icewang on 2017-03-09.
 */
@Service
public class SearchIndexServiceImpl implements SearchIndexService {

    @Autowired
    private SearchCore searchCore;

    @Override
    public List<Index> queryIndexes(String scope, String query, String indexField,
                                    SearchMode searchMode, int maxSize) throws IOException {
        if(StringUtils.isEmpty(scope) || StringUtils.isEmpty(query)
                || StringUtils.isEmpty(indexField)) {
            throw new IllegalArgumentException();
        }
        maxSize = (maxSize <= 0 || maxSize > 100 ? 10 : maxSize);
//        return null;
        return searchCore.getIndexes(scope, query, indexField, searchMode, maxSize);
    }

//    @Override
//    public PageCustom<Index> queryIndexes(
//            String scope, String query, String indexField, SearchMode searchMode,
//            PageRequestCustom pageRequest) throws IOException {
//        if(StringUtils.isEmpty(scope) || StringUtils.isEmpty(query)
//                || StringUtils.isEmpty(indexField)) {
//            throw new IllegalArgumentException();
//        }
//        List<Index> indexes = searchCore.getIndexes(scope, query, indexField, searchMode, 100);
//        int start = (pageRequest.getPageIndex() - 1) * pageRequest.getPageSize();
//        int end = pageRequest.getPageIndex() * pageRequest.getPageSize();
//        int total = indexes.size();
//        if(start >= total) {
//            return JpaPageHelp.convert((long)total, pageRequest, Lists.newArrayList());
//        }
//        end = (end > total ? total : end);
//        List<Index> content = indexes.subList(start, end);
//        return JpaPageHelp.convert((long)total, pageRequest, content);
//    }

    @Override
    public void deleteAllIndexes(String scope) throws IOException {
        if(StringUtils.isEmpty(scope)) {
            throw new IllegalArgumentException();
        }
        searchCore.deleteAllIndexes(scope);
    }

    @Override
    public void addIndex(String scope, Index index) throws IOException {
        if(StringUtils.isEmpty(scope) || index == null) {
            throw new IllegalArgumentException();
        }
//        searchCore.createIndex(scope, index);
    }

//    @Override
//    public void addIndex(String scope, Iterable<IndexField> fields) throws IOException {
//        if(StringUtils.isEmpty(scope) || fields == null) {
//            throw new IllegalArgumentException();
//        }
//        Index index = new Index();
//        index.setFields(Lists.newArrayList(fields));
//        addIndex(scope, index);
//    }

    @Override
    public void addIndexes(String scope, Iterable<Index> indexes) throws IOException {
        if(StringUtils.isEmpty(scope) || indexes == null) {
            throw new IllegalArgumentException();
        }
//        searchCore.createIndexes(scope, Lists.newArrayList(indexes));
    }
}
