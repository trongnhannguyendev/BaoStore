package com.example.baostore.models;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int userid;
    private int quantity;
    private int bookid;

    public OrderDetail() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
}
