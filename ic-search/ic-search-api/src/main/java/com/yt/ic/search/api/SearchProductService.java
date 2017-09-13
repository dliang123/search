package com.yt.ic.search.api;

import com.yt.ic.search.model.IndexDto;

import java.io.IOException;
import java.util.List;

/**
 * 搜索服务
 */
public interface SearchProductService {

    List<IndexDto> searchCarSeries(String queryString, int n) throws IOException;

    List<IndexDto> searchCarProduct(String queryString, int n) throws IOException;

    // TODO 搜索商品 List<?> searchCarItem(String queryString, int n);

    /**
     * 提交搜索内容
     * @param clientId clientId
     * @param userId 用户ID，可能为空
     * @param indexDto 索引内容
     */
    void submitSearchIndex(String clientId, String userId, IndexDto indexDto);

    /**
     *
     * @param clientId
     * @param maxSize
     * @return
     */
    List<IndexDto> getSearchHistory(String clientId, int maxSize);
}
