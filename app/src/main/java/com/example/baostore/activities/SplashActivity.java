package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Api.RetrofitCallBack.getCheckSaveUserSplash;
import static com.example.baostore.testapi.AppHelper.pushNotification;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.MyLocale;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout btnSplash;
    Intent i;
    ApiService service;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyLocale.onCreate(newBase,MyLocale.getLanguage(newBase)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            service = GetRetrofit.getInstance().createRetrofit();


            // ẩn thanh pin
            if (Build.VERSION.SDK_INT >= 16) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }


            // chuyển màn hình Login
            btnSplash = findViewById(R.id.btnSplash);
            btnSplash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //pushNotification(SplashActivity.this, "Splash", "Click on splash");
                    // Kiểm tra dữ liệu người dùng tồn tại
                    if (!SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
                        finish();
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);

                    } else {
                        User user = SharedPrefManager.getInstance(SplashActivity.this).getUser();
                        Log.d(getString(R.string.debug_SplashActivity), user.getUserid() + " " + user.getFullname() + " " + user.getState());
                        JsonObject object = new JsonObject();
                        object.addProperty(USER_EMAIL, user.getEmail());
                        // Kiểm tra xem email có tồn tại không (vì không lưu user password)
                        Call<UserResponse> call = service.checkUserEmailExist(object);
                        call.enqueue(getCheckSaveUserSplash(SplashActivity.this));
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e.toString());
            finish();
            i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }



}