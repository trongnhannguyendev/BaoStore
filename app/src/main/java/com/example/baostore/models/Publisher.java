package com.example.baostore.models;

import java.io.Serializable;

public class Publisher implements Serializable {
    private int publisherid;
    private String publishername;

    public Publisher(){

    }

    public int getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(int publisherid) {
        this.publisherid = publisherid;
    }

    public String getPublishername() {
        return publishername;
    }

    public void setPublishername(String publishername) {
        this.publishername = publishername;
    }
}
