package com.example.baostore.models;

import java.io.Serializable;

public class Author implements Serializable {
    private int authorid;
    private String authorname;

    public Author(){}

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }
}
