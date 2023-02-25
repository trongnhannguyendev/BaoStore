package com.example.baostore.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("get-all-book.php")
    Call<Result> getbook();




    @POST("check-login.php")
    Call<Result> userLogin(
            @Body JsonObject jsonObject
            );

}
