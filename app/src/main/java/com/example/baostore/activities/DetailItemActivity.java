package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_IMAGE_LIST;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;
import static com.example.baostore.Constant.Constants.CART_QUANTITY;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.testapi.RetrofitCallBack.cartAddItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.adapters.BookImageAdapter;
import com.example.baostore.models.BookImage;
import com.example.baostore.models.User;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.testapi.RetrofitCallBack;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DetailItemActivity extends AppCompatActivity {

    RecyclerView recyImages;
    TextView tvTitle, tvPrice, tvDescription;
    MotionButton btnAddToCart, btnToPayment;

    List<BookImage> list = new ArrayList<>();
    BookImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        tvTitle = findViewById(R.id.tvTitle_detail);
        tvPrice = findViewById(R.id.tvPrice_detail);
        tvDescription = findViewById(R.id.tvDescription_detail);
        recyImages = findViewById(R.id.recyImages_detailItem);
        btnAddToCart = findViewById(R.id.btnAddtocart_detail);
        btnToPayment = findViewById(R.id.btnPay_detail);




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

            list = (List<BookImage>) bundle.get(BOOK_IMAGE_LIST);
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyImages);

            recyImages.setLayoutManager(new LinearLayoutManager(DetailItemActivity.this, LinearLayoutManager.HORIZONTAL, false));
            adapter = new BookImageAdapter(DetailItemActivity.this, list);
            recyImages.setAdapter(adapter);

        }


        btnAddToCart.setOnClickListener(view -> {
            addCart(bundle);
        });

        btnToPayment.setOnClickListener(view -> {
            addCart(bundle);


        });


    }

    public void addCart(Bundle bundle) {
        JsonObject object = new JsonObject();
        User user = SharedPrefManager.getInstance(this).getUser();
        int id = user.getUserid();
        object.addProperty(USER_ID, id);
        object.addProperty(BOOK_ID, Integer.parseInt(bundle.get(BOOK_ID).toString()));
        object.addProperty(CART_QUANTITY, 1);

        ApiService service = new GetRetrofit().getRetrofit();
        Call<CartResponse> call = service.insertCart(object);

        call.enqueue(cartAddItem(this));

        //RetrofitCallBack.cartAddItem1(this, object);


    }



}