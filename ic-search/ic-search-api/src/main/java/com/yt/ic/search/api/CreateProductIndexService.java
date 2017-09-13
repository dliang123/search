package com.yt.ic.search.api;

/**
 * Job建立商品索引使用，不对外
 */
public interface CreateProductIndexService {

    void createCarSeriesIndex();

    void createCarProductIndex();

    void createCarItemIndex();

    void deleteCarSeriesIndex();

    void deleteCarProductIndex();
}
