package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;

public class CartInforActivity extends AppCompatActivity {
    TextView tvTitleHeader;
    ImageView imgBack;
    private MotionButton btnConfirmCartInfor;
    private CardView cvIconProgress;

    EditText edFullName, edPhoneNumber, edAddress, edEmail;

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

        // Load user
        edFullName = findViewById(R.id.edFullName_cartinfo);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_cartinfo);
        edAddress = findViewById(R.id.edAddress_cartinfo);
        edEmail = findViewById(R.id.edEmail_cartinfo);

        User user = SharedPrefManager.getInstance(this).getUser();

        edFullName.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhoneNumber());
        edEmail.setText(user.getEmail());
    }
}