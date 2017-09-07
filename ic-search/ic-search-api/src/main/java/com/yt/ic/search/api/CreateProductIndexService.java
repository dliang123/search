package com.yt.ic.search.api;

/**
 * Job建立商品索引使用，不对外
 * Created by icewang on 2017-03-10.
 */
public interface CreateProductIndexService {

    void createCarSeriesIndex();

    void createCarProductIndex();

    void createCarItemIndex();

    void deleteCarSeriesIndex();

    void deleteCarProductIndex();
}
