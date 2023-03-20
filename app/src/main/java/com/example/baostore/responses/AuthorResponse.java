package com.example.baostore.responses;

import com.example.baostore.models.Author;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorResponse {
    @SerializedName("respone_code")
    private int responseCode;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Author> data;

    public AuthorResponse() {
    }

    public AuthorResponse(int responseCode, String error, String message, List<Author> data) {
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

    public List<Author> getData() {
        return data;
    }

    public void setData(List<Author> data) {
        this.data = data;
    }
}
