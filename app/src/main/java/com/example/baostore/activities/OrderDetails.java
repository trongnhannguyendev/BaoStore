package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baostore.R;

public class OrderDetails extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // header
        tvTitleHeader=findViewById(R.id.title);
        tvTitleHeader.setText("Chi tiết đơn hàng");
    }
}