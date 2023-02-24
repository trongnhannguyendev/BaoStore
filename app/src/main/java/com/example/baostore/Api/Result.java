package com.example.baostore.Api;

import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("respone_code")
    private int responseCode;

    @SerializedName("data")
    private JsonArray data;
    private User user;

    public Result(Boolean error, String message, int responseCode, User user) {
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

    public User getUser() {
        JsonArray myArr = data;
        JsonObject jsonObject1 = myArr.get(0).getAsJsonObject();

        int id = Integer.parseInt(jsonObject1.get("userID").toString());
        String email = jsonObject1.get("email").toString();
        String fullName = jsonObject1.get("fullName").toString();
        String phonenumber = jsonObject1.get("phoneNumber").toString();
        return new User(id, email, fullName, phonenumber);
    }
}
