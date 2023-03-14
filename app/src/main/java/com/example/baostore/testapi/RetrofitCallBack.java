package com.example.baostore.testapi;

import static com.example.baostore.Constant.Constants.RESPONSE_NOT_OKAY;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack {
    public static Callback<UserResponse> getFullname(Context context, TextView textView){
        Callback<UserResponse> check_exists_emailCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if (userResponse.getRespone_code() == 0){
                        textView.setText("Email is available");
                    }else if (userResponse.getRespone_code() == 1){
                        textView.setText("Email is existed");
                    }else {
                        textView.setText(userResponse.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                        textView.setText(t.getMessage());
            }
        };
        return check_exists_emailCallback;
    }

    public static Callback<UserResponse> checkLogin(Context context){
        Callback<UserResponse> check_loginCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    if (userResponse.getRespone_code() == RESPONSE_NOT_OKAY){
                        Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }else if (userResponse.getRespone_code() == RESPONSE_OKAY){
                        User user = (User) response.body().getData();
                        SharedPrefManager.getInstance(context).userLogin(user);
                    }else {
                        Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        return check_loginCallback;
    }
    public static Callback<UserResponse> signUpAccount(Context context){
        Callback<UserResponse> signUpAccountCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    if (userResponse.getRespone_code() == 1){
                        Intent intent = new Intent(context, LoginActivity.class);
                        ((LoginActivity)context).finishAffinity();
                        context.startActivity(intent);
                    }
                    Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        return signUpAccountCallback;
    }

    public static Callback<UserResponse> login(Context context){
        Callback<UserResponse> loginCallback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getRespone_code() == 1){
                    SharedPrefManager.getInstance(context).userLogin(response.body().getData().get(0));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("--RetrofitCallBack", t.toString());
            }


        };
        return loginCallback;
    }
}
