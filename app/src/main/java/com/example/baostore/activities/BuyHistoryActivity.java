package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.testapi.RetrofitCallBack.getOrderByUserID;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.Order;
import com.example.baostore.models.User;
import com.example.baostore.responses.OrderResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class BuyHistoryActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    MotionButton btnConfirm;
    List<Order> list = new ArrayList<>();
    RecyclerView recyBuyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_history);

        // header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Lịch sử mua hàng");
        recyBuyHistory = findViewById(R.id.recyBuyHistory);
        recyBuyHistory.setLayoutManager(new LinearLayoutManager(this));


        btnConfirm = findViewById(R.id.btnConfirm_bh);
        btnConfirm.setOnClickListener(view ->{
            finish();
        });

        loadData();

    }

    public void loadData(){
        ApiService service = new GetRetrofit().getRetrofit();
        User user = SharedPrefManager.getInstance(this).getUser();
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID, user.getUserid());
        Call<OrderResponse> call = service.getAllOrderById(object);

        call.enqueue(getOrderByUserID(BuyHistoryActivity.this, recyBuyHistory));

    }
}