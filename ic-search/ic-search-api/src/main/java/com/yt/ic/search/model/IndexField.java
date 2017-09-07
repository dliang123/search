package com.yt.ic.search.model;

import java.io.Serializable;

/**
 * 索引项
 * Created by icewang on 2017-03-09.
 */
public class IndexField implements Serializable {

    private String key;

    private String value;

    private boolean analyzed = false;

    private boolean indexed = false;

    public IndexField(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public IndexField(String key, String value, boolean analyzed, boolean indexed) {
        this(key, value);
        this.analyzed = analyzed;
        this.indexed = indexed;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }
}
