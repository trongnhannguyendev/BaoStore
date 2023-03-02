package com.example.baostore.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;

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
                finish();
                if (SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(i);

            }
        });

    }
}