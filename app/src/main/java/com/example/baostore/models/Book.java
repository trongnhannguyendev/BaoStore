package com.example.baostore.models;

import java.io.Serializable;

public class Book implements Serializable {
    private int bookid;
    private String title;
    private int totalPage;
    private String description;
    private String releasedate;
    private double price;
    private int quantity;
    private int categoryid;
    private int authorid;
    private int publisherid;
    private int state;
    private String url;

    public Book() {
    }

    public Book(int bookid, String title, double price, String url) {
        this.bookid = bookid;
        this.title = title;
        this.price = price;
        this.url = url;
    }

    public Book(int bookid, String title, double price, int quantity, int categoryid, int authorid, int publisherid, String url) {
        this.bookid = bookid;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryid = categoryid;
        this.authorid = authorid;
        this.publisherid = publisherid;
        this.url = url;
    }

    public Book(int bookid, String title, int totalPage, String description, String releasedate, double price, int quantity, int categoryid, int authorid, int publisherid, int state, String url) {
        this.bookid = bookid;
        this.title = title;
        this.totalPage = totalPage;
        this.description = description;
        this.releasedate = releasedate;
        this.price = price;
        this.quantity = quantity;
        this.categoryid = categoryid;
        this.authorid = authorid;
        this.publisherid = publisherid;
        this.state = state;
        this.url = url;
    }

    public int getbookid() {
        return bookid;
    }

    public void setbookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(int publisherid) {
        this.publisherid = publisherid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
