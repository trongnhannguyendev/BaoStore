package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baostore.R;

public class CartPaymentActivity extends AppCompatActivity {
    private LinearLayout btn_hide_show, layout_hide_show;
    ImageView img_funcpayment_arrow;
    TextView tvTitleHeader;
    ImageView imgBack;
    private CardView cvIconProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);


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
