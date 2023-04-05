package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_IMAGE_LIST;
import static com.example.baostore.Constant.Constants.BOOK_OBJECT;
import static com.example.baostore.Constant.Constants.CART_AMOUNT;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Api.RetrofitCallBack.cartAddItem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.adapters.BookImageAdapter;
import com.example.baostore.models.Book;
import com.example.baostore.models.BookImage;
import com.example.baostore.models.User;
import com.example.baostore.responses.CartResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class DetailItemActivity extends AppCompatActivity {
    TextView tvTitle, tvPrice, tvDescription;
    MotionButton btnAddToCart, btnToPayment;
    RecyclerView recyImages;
    BookImageAdapter bookImageAdapter;
    List<BookImage> BookImageList;
    Book book;
    ApiService service;
    Utils utils;
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

        service = GetRetrofit.getInstance().createRetrofit();
        utils = new Utils();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            book = (Book) bundle.getSerializable(BOOK_OBJECT);
            Log.d("--", book.getbookid()+"");

            String title = book.getTitle();
            double price = book.getPrice();

            // Book has no description
            String description;
            tvTitle.setText(title);
            Log.d("--", "book title: " + book.getTitle());
            tvPrice.setText(new Utils().priceToString(price));

            BookImageList = (List<BookImage>) bundle.get(BOOK_IMAGE_LIST);
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyImages);

            recyImages.setLayoutManager(new LinearLayoutManager(DetailItemActivity.this, LinearLayoutManager.HORIZONTAL, false));
            bookImageAdapter = new BookImageAdapter(DetailItemActivity.this, BookImageList);
            recyImages.setAdapter(bookImageAdapter);

        }
        btnAddToCart.setOnClickListener(view -> {
            addCart();
        });

        btnToPayment.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(111, intent);
            finish();
        });
    }

    public void addCart() {
        JsonObject object = new JsonObject();
        User user = SharedPrefManager.getInstance(this).getUser();
        int id = user.getUserid();
        int bookid = book.getbookid();
        object.addProperty(USER_ID, id);
        object.addProperty(BOOK_ID, bookid);
        object.addProperty(CART_AMOUNT, 1);

        Call<CartResponse> call = service.insertCart(object);

        call.enqueue(cartAddItem(this));
    }
}