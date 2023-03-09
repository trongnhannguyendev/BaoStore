package com.example.baostore.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.R;

public class CartPaymentActivity extends AppCompatActivity {
    private LinearLayout btn_hide_show, layout_hide_show;
    ImageView img_funcpayment_arrow;
    TextView tvTitleHeader, tvFullname, tvPhoneNumber, tvAddress,
            tvOrderDate, tvNote, tvBookPrice, tvShipPrice, tvTotalPrice;
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
        tvBookPrice = findViewById(R.id.tvBookPrice_cart);
        tvShipPrice = findViewById(R.id.tvShipPrice_cpayment);
        tvTotalPrice = findViewById(R.id.tvTotalPrice_cart);
        btnConfirm = findViewById(R.id.btnConfirm_CartPayment);

        btnConfirm.setOnClickListener(view ->{
            finish();
        });

        // TODO get order detail
        /*
        Order order = new Order();
        tvFullname.setText(order.getFullname());
        tvPhoneNumber.setText(order.getPhoneNumber());
        tvAddress.setText(order.getAddress());
        tvOrderDate.setText(order.getCreateDate().toString());
        tvNote.setText(order.getNote());
        //tvBookPrice.setText(null);
        //tvShipPrice.setText(null);
        tvTotalPrice.setText(String.valueOf(order.getOrderTotal()));
         */




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
