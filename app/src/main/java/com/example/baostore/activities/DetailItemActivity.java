package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;
import static com.example.baostore.Constant.Constants.IMAGE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.adapters.BookImageAdapter;
import com.example.baostore.models.BookImage;
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


        }
        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject object = new JsonObject();
        object.addProperty(BOOK_ID, Integer.parseInt(bundle.get(BOOK_ID).toString()));

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

    }


}