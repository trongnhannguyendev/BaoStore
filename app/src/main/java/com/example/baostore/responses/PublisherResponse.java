package com.example.baostore.responses;

import com.example.baostore.models.Publisher;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublisherResponse {
    @SerializedName("respone_code")
    private int responseCode;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Publisher> data;

    public PublisherResponse() {
    }

    public PublisherResponse(int responseCode, String error, String message, List<Publisher> data) {
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

    public List<Publisher> getData() {
        return data;
    }

    public void setData(List<Publisher> data) {
        this.data = data;
    }
}
