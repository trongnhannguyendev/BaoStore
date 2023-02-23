package com.example.baostore.models;

public class User {
    private int userID;
    private String email;
    private String password;
    private String fullname;
    private String phoneNumber;


    public User(int userID, String email, String password, String fullname, String phoneNumber) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
    }

    public User(int userID, String email, String fullname, String phoneNumber) {
        this.userID = userID;
        this.email = email;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
