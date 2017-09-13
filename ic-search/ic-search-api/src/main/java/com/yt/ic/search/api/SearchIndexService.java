package com.yt.ic.search.api;

import com.yt.ic.search.model.Index;
import com.yt.ic.search.model.SearchMode;


import java.io.IOException;
import java.util.List;

/**
 * 搜索索引服务
 *
 */
public interface SearchIndexService {

    /**
     * 根据输入范围和词句查询索引，返回结果按照评分高->低排序
     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
     * @param queryString 查询词句
     * @param indexField 索引字段
     * @param maxSize 返回索引最大条数，如果{@code maxSize <= 0 || maxSize > 100}，使用默认值10
     * @return 索引列表，按照评分高->低排序
     */
    List<Index> queryIndexes(String scope, String queryString, String indexField,
                             SearchMode searchMode, int maxSize) throws IOException;

//    /**
//     * 根据输入范围和词句查询索引，分页返回，总条数最大值为100，返回结果按照评分高->低排序
//     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
//     * @param queryString 查询词句
//     * @param indexField 索引字段
//     * @param pageRequest 分页请求
//     * @return
//     */
//    PageCustom<Index> queryIndexes(
//            String scope, String queryString, String indexField, SearchMode searchMode,
//            PageRequestCustom pageRequest) throws IOException;

    /**
     * 删除指定scope的全部索引
     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
     */
    void deleteAllIndexes(String scope) throws IOException;

    /**
     * 添加索引
     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
     * @param index 索引对象
     */
    void addIndex(String scope, Index index) throws IOException;

    /**
     * 添加索引
     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
     * @param fields 索引字段列表
     */
//    void addIndex(String scope, Iterable<IndexField> fields) throws IOException;

    /**
     * 批量添加索引
     * @param scope 索引范围，建立所以时，不同的scope会建立不同的索引
     * @param indexes 索引列表
     */
    void addIndexes(String scope, Iterable<Index> indexes) throws IOException;
}