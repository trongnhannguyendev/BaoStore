package com.example.baostore.models;

public class Address {
    private int addressID;
    private String addressLocation;
    private String ward;
    private String district;
    private String city;
    private String addressName;
    private String isDefault;
    private int userID;

    public Address(){

    }

    public Address(int addressID, String addressLocation) {
        this.addressID = addressID;
        this.addressLocation = addressLocation;
    }

    public Address(int addressID, String addressLocation, String ward, String district, String city, String addressName, String isDefault, int userID) {
        this.addressID = addressID;
        this.addressLocation = addressLocation;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.addressName = addressName;
        this.isDefault = isDefault;
        this.userID = userID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
