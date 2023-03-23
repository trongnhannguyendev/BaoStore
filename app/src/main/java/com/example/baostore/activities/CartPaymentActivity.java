package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.CART_TOTAL_PRICE;
import static com.example.baostore.Constant.Constants.ORDER_ADDRESS;
import static com.example.baostore.Constant.Constants.ORDER_CREATE_DATE;
import static com.example.baostore.Constant.Constants.ORDER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.ORDER_USER_NAME;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.testapi.RetrofitCallBack.addOrder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Order;
import com.example.baostore.responses.OrderResponse;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

public class CartPaymentActivity extends AppCompatActivity {
    private LinearLayout btn_hide_show, layout_hide_show;
    ImageView img_funcpayment_arrow;
    TextView tvTitleHeader, tvFullname, tvPhoneNumber, tvAddress,
            tvOrderDate, tvNote, tvShipPrice, tvTotalPrice;
    ImageView imgBack;
    MotionButton btnConfirm;
    private CardView cvIconProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);

        tvFullname = findViewById(R.id.tvFullname_cpayment);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber_cpayment);
        tvAddress = findViewById(R.id.tvAddress_cpayment);
        tvOrderDate = findViewById(R.id.tvOrderDate_cpayment);
        tvNote = findViewById(R.id.tvNote_cpayment);
        tvShipPrice = findViewById(R.id.tvShipPrice_cpayment);
        tvTotalPrice = findViewById(R.id.totalPrice_cpayment);
        btnConfirm = findViewById(R.id.btnConfirm_CartPayment);

        ApiService service = new GetRetrofit().getRetrofit();


        Bundle bundle = getIntent().getExtras();

        double price = Double.parseDouble(bundle.get(CART_TOTAL_PRICE).toString());
        String orderDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

        String fullName =bundle.get(USER_FULL_NAME).toString();
        String phoneNunber = bundle.get(USER_PHONE_NUMBER).toString();
        String address = bundle.get(ADDRESS_LOCATION).toString();
        double totalPrice =Double.parseDouble(bundle.get(CART_TOTAL_PRICE).toString());
        Log.d("--", "onCreate: "+ totalPrice);

        tvFullname.setText(fullName);
        tvPhoneNumber.setText(phoneNunber);
        tvAddress.setText(address);
        tvOrderDate.setText(orderDate);
        //tvBookPrice.setText(null);
        //tvShipPrice.setText(null);
        tvTotalPrice.setText(new Utils().priceToString(totalPrice));


        btnConfirm.setOnClickListener(view ->{
            JsonObject object = new JsonObject();
            object.addProperty(ORDER_USER_NAME, fullName);
            object.addProperty(ORDER_PHONE_NUMBER, phoneNunber);
            object.addProperty(ORDER_ADDRESS, address);
            object.addProperty(ORDER_CREATE_DATE, orderDate);


            Call<OrderResponse> call = service.addOrder(object);

            call.enqueue(addOrder(this));

            finish();
        });






        // màu icon progress
        cvIconProgress = findViewById(R.id.cvProgress_3);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);

        //header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Chi tiết thanh toán");

        //button back
        imgBack = findViewById(R.id.back_button);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // hide show phương thức thanh toán
        btn_hide_show = findViewById(R.id.btn_hide_show);
        layout_hide_show = findViewById(R.id.layout_hide_show);
        img_funcpayment_arrow = findViewById(R.id.img_funcpayment_arrow);
        btn_hide_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isvisible = layout_hide_show.getVisibility();
                if (isvisible == View.VISIBLE) {
                    layout_hide_show.setVisibility(View.GONE);
                    img_funcpayment_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));


                } else {
                    layout_hide_show.setVisibility(View.VISIBLE);
                    img_funcpayment_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
    }
}
