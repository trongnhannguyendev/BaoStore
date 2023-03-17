package com.example.baostore.DAOs;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;
import static com.example.baostore.Constant.Constants.CART_QUANTITY;
import static com.example.baostore.Constant.Constants.USER_ID;

import com.example.baostore.models.Cart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public CartDAO(){}

    public List<Cart> getData(JsonArray array){
        List<Cart> list = new ArrayList<>();
        for(JsonElement element1 : array) {
            JsonObject object = element1.getAsJsonObject();
            int userID = object.get(USER_ID).getAsInt();
            int bookID = object.get(BOOK_ID).getAsInt();
            int quantity = object.get(CART_QUANTITY).getAsInt();
            String title = object.get(BOOK_TITLE).getAsString();
            double price = object.get(BOOK_PRICE).getAsDouble();
            String url = object.get(BOOK_URL).getAsString();

            Cart cart = new Cart();

            cart.setBookid(bookID);
            cart.setQuantity(quantity);
            cart.setTitle(title);
            cart.setPrice(price);
            cart.setUrl(url);

            list.add(cart);
        }
        return list;
    }
}
