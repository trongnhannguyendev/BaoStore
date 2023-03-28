package com.example.baostore.Api;

import android.content.Context;

import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static GetRetrofit Instance;


    public static synchronized GetRetrofit getInstance(){
        if(Instance == null){
            Instance = new GetRetrofit();
        }
        return Instance;
    }

    public ApiService createRetrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        return service;

    }
}
