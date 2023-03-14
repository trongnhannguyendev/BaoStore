package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baostore.R;

public class BuyHistoryActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    MotionButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_history);

        // header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Lịch sử mua hàng");

        btnConfirm = findViewById(R.id.btnConfirm_bh);
        btnConfirm.setOnClickListener(view ->{
            finish();
        });

    }
}