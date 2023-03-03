package com.example.baostore.Api;

import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {

    public ApiService getRetrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        return service;

    }
}
