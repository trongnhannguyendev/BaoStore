package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.testapi.RetrofitCallBack.getCheckSaveUserSplash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.example.baostore.testapi.AppHelper;
import com.google.gson.JsonObject;

import retrofit2.Call;

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
                AppHelper.pushNotification(SplashActivity.this,"Thông báo","Đăng nhập");

                User user = SharedPrefManager.getInstance(SplashActivity.this).getUser();
                Log.d("---sdrognp", user.getUserid() +" " + user.getFullname()+" " + user.getState());

                if(!SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()){
                    finish();
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                } else {
                    ApiService service = new GetRetrofit().getRetrofit();

                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    Call<UserResponse> call = service.checkUserEmailExist(object);

                    call.enqueue(getCheckSaveUserSplash(SplashActivity.this));

                }
            }
        });

    }

}