package com.example.baostore.models;

import java.io.Serializable;

public class Address implements Serializable {
    private int addressid;
    private String location;
    private String ward;
    private String district;
    private String city;
    private String addressname;
    private int isdefault;
    private int userid;

    public Address(){

    }

    public Address(int addressid, String location) {
        this.addressid = addressid;
        this.location = location;
    }

    public Address(int addressid, String location, String ward, String district, String city, String addressname, int isdefault, int userid) {
        this.addressid = addressid;
        this.location = location;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.addressname = addressname;
        this.isdefault = isdefault;
        this.userid = userid;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
