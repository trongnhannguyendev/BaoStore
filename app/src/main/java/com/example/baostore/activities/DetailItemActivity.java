package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;

public class DetailItemActivity extends AppCompatActivity {

    ImageView ivBook;
    TextView tvTitle, tvPrice, tvDescription;
    MotionButton btnAddToCart, btnToPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        ivBook = findViewById(R.id.ivBook_detail);
        tvTitle = findViewById(R.id.tvTitle_detail);
        tvPrice = findViewById(R.id.tvPrice_detail);
        tvDescription = findViewById(R.id.tvDescription_detail);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.get(BOOK_TITLE).toString();
            double price = (double) bundle.get(BOOK_PRICE);
            // Book has no description
            String description;
            String url = bundle.get(BOOK_URL).toString();
            Log.d("--------------------------------", bundle.get(BOOK_TITLE).toString());
            tvTitle.setText(title);
            tvPrice.setText(new Utils().priceToString(price));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = new Utils().loadImageFromURL(url);
                    ivBook.post(new Runnable() {
                        @Override
                        public void run() {
                            ivBook.setImageBitmap(bitmap);
                        }
                    });
                }
            });
            thread.start();
        }

    }
}