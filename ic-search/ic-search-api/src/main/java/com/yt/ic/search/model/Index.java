package com.yt.ic.search.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 搜索索引对象
 * Created by icewang on 2017-03-09.
 */
public class Index implements Serializable {

    private Float score;

    private Collection<IndexField> fields;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Collection<IndexField> getFields() {
        return fields;
    }

    public void setFields(Collection<IndexField> fields) {
        this.fields = fields;
    }
}