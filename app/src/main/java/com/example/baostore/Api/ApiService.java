package com.example.baostore.Api;

import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.AuthorResponse;
import com.example.baostore.responses.BookImageResponse;
import com.example.baostore.responses.BookResponse;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.responses.CategoryResponse;
import com.example.baostore.responses.OrderResponse;
import com.example.baostore.responses.PublisherResponse;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // USER

    @POST("check-login.php")
    Call<UserResponse> userLogin(
            @Body JsonObject jsonObject
    );

    @POST("register-user.php")
    Call<UserResponse> registerUser(
            @Body JsonObject jsonObject
    );

    @POST("check-email-exists-email.php")
    Call<UserResponse> checkUserEmailExist(
            @Body JsonObject jsonObject
    );

    @POST("get-all-address-by-user.php")
    Call<AddressResponse> getAddressByUser(
            @Body JsonObject jsonObject
    );

    @POST("update-email.php")
    Call<UserResponse> updateEmail(
            @Body JsonObject jsonObject
    );

    @POST("update-full-name.php")
    Call<UserResponse> updateFullname(
            @Body JsonObject jsonObject
    );

    @POST("update-password.php")
    Call<UserResponse> updatePassword(
            @Body JsonObject jsonObject
    );

    @POST("update-phone-number.php")
    Call<UserResponse> updatePhoneNumber(
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
    Call<BookResponse> getbook();

    @POST("get-image-by-bookid.php")
    Call<BookImageResponse> getImagesByBookID(
            @Body JsonObject jsonObject
    );


    // Category

    @GET("get-all-categories.php")
    Call<CategoryResponse> getCategories();

    // Publisher

    @GET("get-all-publishers.php")
    Call<PublisherResponse> getPublishers();

    // Author
    @GET("get-all-authors.php")
    Call<AuthorResponse> getAuthors();


    // Cart
    @POST("get-cart-by-user.php")
    Call<CartResponse> getCartByUserID(
            @Body JsonObject jsonObject
    );

    @POST("get-remove-cart.php")
    Call<CartResponse> deleteCart(
            @Body JsonObject jsonObject
    );

    @POST("insert-cart.php")
    Call<CartResponse> insertCart(
            @Body JsonObject jsonObject
    );

    @POST("get-minus-quantity-cart.php")
    Call<CartResponse> decreaseCartQuantity(
            @Body JsonObject jsonObject
    );

    @POST("get-add-quantity-cart.php")
    Call<CartResponse> increaseCartQuantity(
            @Body JsonObject jsonObject
    );

    @POST("insert-order.php")
    Call<OrderResponse> addOrder(
            @Body JsonObject jsonObject
    );

    @POST("get-all-order-by-user.php")
    Call<OrderResponse> getAllOrderById(
            @Body JsonObject jsonObject
    );

    @POST("insert-order-detail.php")
    Call<OrderResponse> addOrderDetail(
            @Body JsonObject jsonObject
    );

}
