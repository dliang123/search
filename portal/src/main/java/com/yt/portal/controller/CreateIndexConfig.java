package com.yt.portal.controller;

import org.springframework.context.annotation.PropertySource;

/**
 * Created by jackdeng on 2017/9/12.
 */
@PropertySource(value = "Abc")
public class AA {
    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
