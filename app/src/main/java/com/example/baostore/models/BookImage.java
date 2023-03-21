package com.example.baostore.models;

import java.io.Serializable;

public class BookImage implements Serializable {
    private String url;

    public BookImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
