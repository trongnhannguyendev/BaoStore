package com.example.baostore.Api;

import android.util.Log;

import com.example.baostore.models.Book;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BookResult {


    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("respone_code")
    private int responseCode;

    @SerializedName("data")
    private JsonArray data;

    private List<Book> book;

    public BookResult(Boolean error, String message, int responseCode) {
        this.error = error;
        this.message = message;
        this.responseCode = responseCode;
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

    public List<Book> getBook() {
        JsonArray myArr = data;
        List<Book> list = new ArrayList<>();
        for(JsonElement jsonElement: myArr){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int bookID = jsonObject.get("bookid").getAsInt();
            String title = jsonObject.get("title").getAsString();
            double price = jsonObject.get("price").getAsDouble();
            String url = jsonObject.get("url").getAsString();

            Book book = new Book(bookID,title, price,url);
            Log.d("--------------------",book.getTitle());
            list.add(book);

        }
        return list;
    }
}
