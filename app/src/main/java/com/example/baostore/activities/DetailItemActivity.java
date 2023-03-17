package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_IMAGE_LIST;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;
import static com.example.baostore.Constant.Constants.CART_QUANTITY;
import static com.example.baostore.Constant.Constants.IMAGE_URL;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.testapi.RetrofitCallBack.cartAddItem;
import static com.example.baostore.testapi.RetrofitCallBack.userAddressGetAll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.adapters.BookImageAdapter;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.models.BookImage;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ApiService service = new GetRetrofit().getRetrofit();



        btnAddToCart.setOnClickListener(view ->{
            addCart(bundle);
        });

        btnToPayment.setOnClickListener(view ->{
            addCart(bundle);





        });




    }

    public void addCart(Bundle bundle){
        JsonObject object = new JsonObject();
        User user = SharedPrefManager.getInstance(this).getUser();
        int id = user.getUserid();
        object.addProperty(USER_ID, id);
        object.addProperty(BOOK_ID, Integer.parseInt(bundle.get(BOOK_ID).toString()));
        object.addProperty(CART_QUANTITY, 1);
        cartAddItem(this, object);


    }



    /*public void loadImages(Bundle bundle, ApiService service){
        JsonObject object = new JsonObject();
        object.addProperty(BOOK_ID, Integer.parseInt(bundle.get(BOOK_ID).toString()));
        Log.d("---DetailItemActivity", "Bookid: "+bundle.get(BOOK_ID));
        Call<Result> call = service.getImagesByBookID(object);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                JsonElement element = response.body().getData();
                JsonArray array = element.getAsJsonArray();
                for(JsonElement jsonElement : array){
                    JsonObject object1 = jsonElement.getAsJsonObject();
                    BookImage bookImage = new BookImage();
                    bookImage.setUrl(object1.get(IMAGE_URL).getAsString());

                    list.add(bookImage);
                }
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(recyImages);

                recyImages.setLayoutManager(new LinearLayoutManager(DetailItemActivity.this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new BookImageAdapter(DetailItemActivity.this, list);
                recyImages.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("----DetailItemActivity", t.toString());
            }
        });
    }*/


}