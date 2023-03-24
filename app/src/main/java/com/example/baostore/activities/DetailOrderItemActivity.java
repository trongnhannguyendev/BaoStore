package com.example.baostore.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.R;

public class DetailOrderItemActivity extends AppCompatActivity {
    public TextView tvDate, tvTotalPrice, tvPaymentType, tvReceiverName, tvReceiverPhoneNumber,tvReceiverAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_item);
        tvDate = findViewById(R.id.tvOrderDate_detailorderitem);
        tvTotalPrice = findViewById(R.id.tvTotalPrice_detailorderitem);
        tvPaymentType = findViewById(R.id.tvPaymentType_orderhistoryitem);
        tvReceiverName = findViewById(R.id.tvReceiverName_detailorder);
        tvReceiverPhoneNumber = findViewById(R.id.tvReceiverPhoneNumber_detailorder);
        tvReceiverAddress = findViewById(R.id.tvReceiverAddress_orderhistoryitem);

        // TODO: get order detail

    }
}