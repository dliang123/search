package com.yt.portal.controller;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jackdeng on 2017/9/12.
 */
@ConfigurationProperties(value = "createIndexConfig.yml")
public class CreateIndexConfig {
    private String series;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
