package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Api.RetrofitCallBack.getOrderByUserID;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.OrderResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class BuyHistoryActivity extends AppCompatActivity {
    TextView tvTitleHeader;
    RecyclerView recyBuyHistory;

    Bundle bundle;
    ApiService service;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_buy_history);

            imgBack = findViewById(R.id.back_button);
            tvTitleHeader = findViewById(R.id.title);
            recyBuyHistory = findViewById(R.id.recyBuyHistory);

            // header
            tvTitleHeader.setText(getResources().getString(R.string.header_buy_history));

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            recyBuyHistory.setLayoutManager(new LinearLayoutManager(this));

            service = GetRetrofit.getInstance().createRetrofit();
            bundle = getIntent().getExtras();

            loadData();
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }
    }

    public void loadData(){
        User user = SharedPrefManager.getInstance(this).getUser();
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID, user.getUserid());
        Call<OrderResponse> call = service.getAllOrderById(object);
        call.enqueue(getOrderByUserID(BuyHistoryActivity.this, recyBuyHistory));
    }
}