package com.yt.ic.search.enums;

/**
 * Created by jackdeng on 2017/9/12.
 */
public enum SeriesScopeEnum {
    SeriesScopeA("car/seriesA"),
    SeriesScopeB("car/seriesB");

    private String path;

    SeriesScopeEnum(String _path) {
        path = _path;
    }

    public String getPath() {
        return path;
    }

}
