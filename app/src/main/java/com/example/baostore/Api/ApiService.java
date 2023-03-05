package com.example.baostore.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // USER

    @POST("check-login.php")
    Call<Result> userLogin(
            @Body JsonObject jsonObject
    );

    @POST("register-user.php")
    Call<Result> registerUser(
            @Body JsonObject jsonObject
    );

    @POST("check-email-exists-email.php")
    Call<Result> checkUserEmailExist(
            @Body JsonObject jsonObject
    );

    @POST("get-all-address-by-user.php")
    Call<Result> getAddressByUser(
            @Body JsonObject jsonObject
    );

    @POST("update-email.php")
    Call<Result> updateEmail(
            @Body JsonObject jsonObject
    );

    @POST("update-full-name.php")
    Call<Result> updateFullname(
            @Body JsonObject jsonObject
    );

    @POST("update-password.php")
    Call<Result> updatePassword(
            @Body JsonObject jsonObject
    );

    @POST("update-phone-number.php")
    Call<Result> updatePhoneNumber(
            @Body JsonObject jsonObject
    );

    @POST("active-user.php")
    Call<Result> activeUser(
            @Body JsonObject jsonObject
    );

    @POST("deactive-user.php")
    Call<Result> deactiveUser(
            @Body JsonObject jsonObject
    );

    //BOOK
    @GET("get-all-book.php")
    Call<Result> getbook();

    @POST("get-books-by-category.php")
    Call<Result> getBookByCategory(
            @Body JsonObject jsonObject
    );

    // Category

    @GET("get-all-categories.php")
    Call<Result> getCategories();

    // Publisher

    @GET("get-all-publishers.php")
    Call<Result> getPublishers();

    // Cart
    @POST("get-cart-by-userID.php")
    Call<Result> getCartByUserID(
            @Body JsonObject jsonObject
    );


}
