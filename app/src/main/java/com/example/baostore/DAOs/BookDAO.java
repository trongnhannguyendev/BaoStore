package com.example.baostore.DAOs;

import static com.example.baostore.Constant.Constants.BOOK_AUTHOR_ID;
import static com.example.baostore.Constant.Constants.BOOK_CATEGORY_ID;
import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_PUBLISHER_ID;
import static com.example.baostore.Constant.Constants.BOOK_QUANTITY;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;

import android.content.Context;
import android.util.Log;

import com.example.baostore.models.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Context context;

    public BookDAO(Context context) {
        this.context = context;
    }

    public List<Book> getData(JsonArray array) {
        List<Book> list = new ArrayList<>();

        for (JsonElement jsonElement : array) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int bookID = jsonObject.get(BOOK_ID).getAsInt();
            String title = jsonObject.get(BOOK_TITLE).getAsString();
            double price = jsonObject.get(BOOK_PRICE).getAsDouble();
            int quantity = jsonObject.get(BOOK_QUANTITY).getAsInt();
            int categoryID = jsonObject.get(BOOK_CATEGORY_ID).getAsInt();
            int authorID = jsonObject.get(BOOK_AUTHOR_ID).getAsInt();
            int publisherID = jsonObject.get(BOOK_PUBLISHER_ID).getAsInt();
            String url = jsonObject.get(BOOK_URL).getAsString();

            Book book = new Book();
            book.setBookID(bookID);
            book.setTitle(title);
            book.setPrice(price);
            book.setQuantity(quantity);
            book.setCategoryID(categoryID);
            book.setAuthorID(authorID);
            book.setPublisherID(publisherID);
            book.setUrl(url);

            Log.d("----BOOK", book.getTitle());
            list.add(book);
        }
        return list;
    }

}
