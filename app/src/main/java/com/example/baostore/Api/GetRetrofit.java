package com.example.baostore.Api;

import android.content.Context;

import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    public static GetRetrofit Instance;
    public static synchronized GetRetrofit getInstance(Context context){
        if(Instance == null){
            Instance = new GetRetrofit();
        }
        return Instance;
    }

    public ApiService getRetrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        return service;

    }
}
