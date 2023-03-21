package com.example.baostore.Api;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("respone_code")
    private int responseCode;

    @Nullable
    @SerializedName("data")
    private JsonElement data;

    public Result(Boolean error, String message, int responseCode, JsonElement data) {
        this.error = error;
        this.message = message;
        this.responseCode = responseCode;
        Log.d("-----------------------------", "is JSON NULL");

        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public JsonElement getData() {
        return data;
    }


}
