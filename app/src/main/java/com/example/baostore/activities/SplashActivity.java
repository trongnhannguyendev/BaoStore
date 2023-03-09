package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.USER_STATE;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout btnSplash;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }


        // chuyển màn hình Login
        btnSplash = findViewById(R.id.btnSplash);
        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSaveUser();

            }
        });

    }

    private void checkSaveUser(){
        User user = SharedPrefManager.getInstance(this).getUser();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }

        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject emailObj = new JsonObject();
        emailObj.addProperty(USER_EMAIL, user.getEmail());

        Call<Result> emailCall = service.checkUserEmailExist(emailObj);

        emailCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // Lấy thông tin từ server
                boolean callError = response.body().getError();
                int responseCode = response.body().getResponseCode();
                String callMsg = response.body().getMessage();
                String log = new Utils().logEnqueueMsg(callError, responseCode, callMsg);
                Log.d(String.valueOf(R.string.debug_RegisterActivity), log);

                i = new Intent(SplashActivity.this, LoginActivity.class);

                // Kiểm tra email có tồn tại trong server
                if(responseCode == 1) {
                    JsonElement element = response.body().getData();
                    JsonArray array = element.getAsJsonArray();
                    JsonObject object = array.get(0).getAsJsonObject();

                    i = new Intent(SplashActivity.this, MainActivity.class);

                    int state = object.get(USER_STATE).getAsInt();
                    String fullName = object.get(USER_FULL_NAME).getAsString();
                    String phoneNumber = object.get(USER_PHONE_NUMBER).getAsString();

                    // Kiểm tra dữ liệu không đồng nhất -> cập nhật SharedPref
                    if(!user.getFullname().equals(fullName) || !user.getPhoneNumber().equals(phoneNumber)){
                        user.setFullname(fullName);
                        user.setPhoneNumber(phoneNumber);

                        SharedPrefManager.getInstance(SplashActivity.this).userLogin(user);
                    }

                    // Kiểm tra tình trạng tài khoản

                    if (state != 1) {
                        SharedPrefManager.getInstance(SplashActivity.this).logout();
                        Toast.makeText(SplashActivity.this, "Tài khoản đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                    }


                }

                // Email không tồn tại
                else{
                    SharedPrefManager.getInstance(SplashActivity.this).logout();
                    Toast.makeText(SplashActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                finish();
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(SplashActivity.this, R.string.enqueue_failure, Toast.LENGTH_SHORT).show();
                Log.d(String.valueOf(R.string.debug_SplashActivity), t.toString());
            }
        });

    }
}