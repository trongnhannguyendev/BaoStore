package com.example.baostore.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {



    @POST("check-login.php")
    Call<Result> userLogin(
            @Body JsonObject jsonObject
            );

}
