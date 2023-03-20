package com.example.baostore.responses;

import com.example.baostore.models.Address;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {
    @SerializedName("respone_code")
    private int responseCode;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Address> data;

    public AddressResponse() {
    }

    public AddressResponse(int responseCode, String error, String message, List<Address> data) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int respone_code) {
        this.responseCode = respone_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
