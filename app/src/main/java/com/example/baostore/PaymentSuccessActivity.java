package com.example.baostore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Bundle;

import com.example.baostore.activities.MainActivity;

public class PaymentSuccessActivity extends AppCompatActivity {
    MotionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        button = findViewById(R.id.btnGoBack_ps);

        button.setOnClickListener(view->{
            startActivity(new Intent(PaymentSuccessActivity.this, MainActivity.class));
        });
    }
}