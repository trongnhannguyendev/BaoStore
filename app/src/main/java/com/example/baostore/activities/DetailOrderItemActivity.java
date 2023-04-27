package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ORDER_ID;
import static com.example.baostore.Constant.Constants.ORDER_OBJECT;
import static com.example.baostore.Api.RetrofitCallBack.getOrderDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Order;
import com.example.baostore.responses.OrderDetailResponse;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import retrofit2.Call;

public class DetailOrderItemActivity extends AppCompatActivity {
    public TextView tvDate, tvTotalPrice, tvPaymentType, tvReceiverName, tvReceiverPhoneNumber,tvReceiverAddress;
    public RecyclerView recyOrderDetail;
    ApiService service;
    double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_item);

            tvDate = findViewById(R.id.tvOrderDate_detailorderitem);
            tvTotalPrice = findViewById(R.id.tvTotalPrice_detailorderitem);
            tvPaymentType = findViewById(R.id.tvPaymentType_orderhistoryitem);
            tvReceiverName = findViewById(R.id.tvReceiverName_detailorder);
            tvReceiverPhoneNumber = findViewById(R.id.tvReceiverPhoneNumber_detailorder);
            tvReceiverAddress = findViewById(R.id.tvReceiverAddress_orderhistoryitem);
            recyOrderDetail = findViewById(R.id.recyDetailOrder_detailorder);

            recyOrderDetail.setLayoutManager(new LinearLayoutManager(this));

            service = GetRetrofit.getInstance().createRetrofit();

            Bundle bundle = getIntent().getExtras();
            Order order = (Order) bundle.getSerializable(ORDER_OBJECT);

            JsonObject object = new JsonObject();
            object.addProperty(ORDER_ID, order.getOrderid());

            Call<OrderDetailResponse> orderDetailCall = service.getOrderDetailById(object);
            orderDetailCall.enqueue(getOrderDetail(this, recyOrderDetail, tvTotalPrice));

            tvDate.setText(order.getCreatedate());
            //tvTotalPrice = order.getOrderTotal()
            tvPaymentType.setText(order.getPayment() + "");
            tvReceiverName.setText(order.getFullname());
            tvReceiverPhoneNumber.setText(order.getPhonenumber());
            tvReceiverAddress.setText(order.getAddress());

            checkTotalPrice();
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }
    }


    public void checkTotalPrice(){
        if(!tvTotalPrice.getText().toString().isEmpty()){
            totalPrice = Double.parseDouble(tvTotalPrice.getText().toString());
            tvTotalPrice.setText(new Utils().priceToString(totalPrice));
        } else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkTotalPrice();
                }
            },1000);
        }
    }


}