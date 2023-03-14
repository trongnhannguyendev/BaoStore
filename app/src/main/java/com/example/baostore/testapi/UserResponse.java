package com.example.baostore.testapi;

import com.example.baostore.models.User;

import java.util.List;

public class UserResponse {
    private int respone_code;
    private String error;
    private String message;
    private List<User> data;

    public UserResponse() {
    }

    public UserResponse(int respone_code, String error, String message, List<User> data) {
        this.respone_code = respone_code;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public int getRespone_code() {
        return respone_code;
    }

    public void setRespone_code(int respone_code) {
        this.respone_code = respone_code;
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

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
