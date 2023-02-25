package com.example.baostore.DAOs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempUserDAO extends AsyncTask<String,Void,String> {
    private final Context context;
    public int responseCode = -1;
    LoginActivity loginActivity;

    public TempUserDAO(Context context, LoginActivity loginActivity) {
        this.context = context;
        this.loginActivity = loginActivity;
    }

    public void login(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("password",password);

        Call<Result> call = service.userLogin(jsonObject);


            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    try {
                        responseCode = response.body().getResponseCode();
                        if (!response.body().getError()) {
                            Log.d("-------------", response.body().getError() + "");
                            Log.d("-------------", response.body().getMessage() + "");
                            Log.d("-------------", response.body().getResponseCode() + "");
                            Log.d("-------------", response.body().getUser() + "");
                            if (response.body().getResponseCode() == 1) {
                                SharedPrefManager.getInstance(context).userLogin(response.body().getUser());
                                loginActivity.finish();
                                loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
                            } else {
                                Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("-------------", response.body().getError() + "");
                            Log.d("-------------", response.body().getMessage() + "");
                        }
                    } catch (Exception e) {
                        responseCode = -2;
                        Toast.makeText(context, "Cant get response body error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.d("-------------", t.getMessage() + "");
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            });

    }


    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
