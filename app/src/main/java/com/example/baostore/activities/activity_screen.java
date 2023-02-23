package com.example.baostore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.R;

public class activity_screen extends AppCompatActivity {
    Button btnToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        btnToLogin = findViewById(R.id.button2);

        btnToLogin.setOnClickListener(view ->{
            startActivity(new Intent(this, activity_login.class));
            finish();
        });
    }

}
