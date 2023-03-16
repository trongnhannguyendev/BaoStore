package com.example.baostore.models;

import java.io.Serializable;

public class Book implements Serializable {
    private int bookid;
    private String title;
    private int totalPage;
    private String description;
    private String releaseDate;
    private double price;
    private int quantity;
    private int categoryID;
    private int authorID;
    private int publisherID;
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

    public Book(int bookid, String title, double price, int quantity, int categoryID, int authorID, int publisherID, String url) {
        this.bookid = bookid;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.url = url;
    }

    public Book(int bookid, String title, int totalPage, String description, String releaseDate, double price, int quantity, int categoryID, int authorID, int publisherID, int state, String url) {
        this.bookid = bookid;
        this.title = title;
        this.totalPage = totalPage;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.authorID = authorID;
        this.publisherID = publisherID;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
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
