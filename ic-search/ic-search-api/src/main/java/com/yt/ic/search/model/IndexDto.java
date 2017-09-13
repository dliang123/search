package com.yt.ic.search.model;

import java.io.Serializable;
import java.util.Map;

/**
 */
public class IndexDto implements Serializable {

    private IndexType type;

    private String id;

    private String content;

    private Map<String, String> extra;

    public IndexType getType() {
        return type;
    }

    public void setType(IndexType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
