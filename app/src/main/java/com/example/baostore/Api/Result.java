package com.example.baostore.Api;

import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("respone_code")
    private int responseCode;

    @SerializedName("data")
    private JsonArray user;

    public Result(Boolean error, String message, int responseCode, JsonArray user) {
        this.error = error;
        this.message = message;
        this.responseCode = responseCode;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getResponseCode(){
        return responseCode;
    }

    public JsonArray getUser() {
        return user;
    }
}
