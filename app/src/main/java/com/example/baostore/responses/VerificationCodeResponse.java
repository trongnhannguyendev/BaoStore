package com.example.baostore.responses;

import com.example.baostore.models.User;
import com.example.baostore.models.VerificationCode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerificationCodeResponse {
    @SerializedName("respone_code")
    private int responseCode;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private VerificationCode data;

    public VerificationCodeResponse() {
    }

    public VerificationCodeResponse(int responseCode, String error, String message, VerificationCode data) {
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

    public VerificationCode getData() {
        return data;
    }

    public void setData(VerificationCode data) {
        this.data = data;
    }
}
