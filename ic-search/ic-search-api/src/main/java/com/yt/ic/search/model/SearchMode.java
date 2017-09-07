package com.yt.ic.search.model;

/**
 * 搜索模式
 * Created by icewang on 2017-03-10.
 */
public enum SearchMode {

    /**
     * 分词，所有词均存在
     */
    ANALYZED_AND_ALL_IN,

    /**
     * 分词，任意一个存在
     */
    ANALYZED_AND_ANY_IN,

    /**
     * 不分词
     */
    NOT_ANALYZED;
}
