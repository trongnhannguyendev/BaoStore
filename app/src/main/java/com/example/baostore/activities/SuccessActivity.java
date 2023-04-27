package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Bundle;

import com.example.baostore.R;

public class SuccessActivity extends AppCompatActivity {
    MotionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        button = findViewById(R.id.btnGoBack_ps);

        button.setOnClickListener(view->{
            startActivity(new Intent(SuccessActivity.this, MainActivity.class));
        });
    }
}

