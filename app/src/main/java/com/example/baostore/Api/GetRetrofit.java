package com.example.baostore.Api;

import android.content.Context;

import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static GetRetrofit Instance;
    private static final String BASE_URL = "http://172.16.98.18/bookplatform/views/";


    public static synchronized GetRetrofit getInstance(){
        if(Instance == null){
            Instance = new GetRetrofit();
        }
        return Instance;
    }

    public ApiService createRetrofit() {

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        return service;

    }
}
