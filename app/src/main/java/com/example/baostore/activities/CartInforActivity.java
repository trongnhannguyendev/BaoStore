package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baostore.R;

public class CartInforActivity extends AppCompatActivity {
    TextView tvTitleHeader;
    ImageView imgBack;
    private MotionButton btnConfirmCartInfor;
    private CardView cvIconProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_infor);




                // màu icon progress
                cvIconProgress = findViewById(R.id.cvProgress_2);
        int color = getResources().getColor(R.color.ic_progress);
            cvIconProgress.setCardBackgroundColor(color);


        //header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Thông tin nhận hàng");

        //button back
        imgBack = findViewById(R.id.back_button);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // xử lý button
        btnConfirmCartInfor = findViewById(R.id.btnConfirmCartInfor);
        btnConfirmCartInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartInforActivity.this, CartPaymentActivity.class);
                startActivity(i);


            }
        });
    }
}