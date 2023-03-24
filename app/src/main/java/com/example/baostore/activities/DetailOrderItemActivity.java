package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.ORDER_ID;
import static com.example.baostore.Constant.Constants.ORDER_OBJECT;
import static com.example.baostore.testapi.RetrofitCallBack.getOrderDetail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.models.Book;
import com.example.baostore.models.Order;
import com.example.baostore.responses.OrderDetailResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class DetailOrderItemActivity extends AppCompatActivity {
    public TextView tvDate, tvTotalPrice, tvPaymentType, tvReceiverName, tvReceiverPhoneNumber,tvReceiverAddress;
    public RecyclerView recyOrderDetail;

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
        recyOrderDetail = findViewById(R.id.recyDetailOrder_detailorder);

        recyOrderDetail.setLayoutManager(new LinearLayoutManager(this));

        // TODO: get order detail

        Bundle bundle = getIntent().getExtras();
        Order order = (Order) bundle.getSerializable(ORDER_OBJECT);
        List<Book> bookList = (List<Book>) bundle.getSerializable(BOOK_LIST);
        tvDate.setText(order.getCreatedate());
        //tvTotalPrice = order.getOrderTotal()
        tvPaymentType.setText(order.getPayment()+"");
        tvReceiverName.setText(order.getFullname());
        tvReceiverPhoneNumber.setText(order.getPhonenumber());
        tvReceiverAddress.setText(order.getAddress());

        JsonObject object = new JsonObject();
        object.addProperty(ORDER_ID,order.getOrderid());

        ApiService service = GetRetrofit.Instance.getRetrofit();
        Call<OrderDetailResponse> call = service.getOrderDetailById(object);
        call.enqueue(getOrderDetail(this, recyOrderDetail, tvTotalPrice));
    }
}