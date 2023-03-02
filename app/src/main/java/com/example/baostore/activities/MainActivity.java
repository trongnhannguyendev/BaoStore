package com.example.baostore.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.DAOs.BookDAO;
import com.example.baostore.R;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.fragments.HomeFragment;
import com.example.baostore.fragments.ProfileFragment;
import com.example.baostore.fragments.SearchFragment;
import com.example.baostore.models.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
    ImageView imgBack;
    TextView tvTitleHeader;
    BookDAO dao;
    Fragment fragment;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new BookDAO(this);
        progressBar = findViewById(R.id.progressBar_Main);

        // header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Trang chủ");

        // button back
        imgBack = findViewById(R.id.back_button);
        imgBack.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        bottomNavigationView = findViewById(R.id.myBottomNav);
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);


        // Thêm HomeFragment vào FrameLayout
        fragment = new HomeFragment();
        loadBooksToFragment(fragment);


        // Title toolbar


        //Điều hướng navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    progressBar.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    loadBooksToFragment(fragment);
                    return true;
                case R.id.Search:

                    tvTitleHeader.setText("Tìm kiếm");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SearchFragment()).commit();
                    return true;
                case R.id.Cart:

                    tvTitleHeader.setText("Giỏ hàng");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CartFragment()).commit();
                    return true;
                case R.id.User:

                    tvTitleHeader.setText("Người dùng");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                    return true;
            }
            return false;
        });


        //
//        AppBarLayout appBarLayout = findViewById(R.id.myAppBarLayout);
//        Toolbar toolbar = findViewById(R.id.myToolbarTest);
//        NestedScrollView nestedScrollView = findViewById(R.id.myNestedScroll);
//
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShown = true;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    // khi thanh toolbar ở trên cùng
//                    isShown = true;
//                } else if(isShown) {
//                    // khi thanh toolbar không ở trên cùng
//                    isShown = false;
//                    nestedScrollView.post(() -> nestedScrollView.scrollTo(0, toolbar.getHeight()));
//                }
//            }
//        });


    }


    public void loadBooksToFragment(Fragment fragment) {
        ApiService service = new GetRetrofit().getRetrofit();


        Call<Result> call = service.getbook();
        call.enqueue(new Callback<Result>() {
                         @Override
                         public void onResponse(Call<Result> call, Response<Result> response) {
                             JsonElement element = response.body().getData();
                             JsonArray myArr = element.getAsJsonArray();

                             List<Book> list = new ArrayList<>();
                             list = dao.getData(myArr);

                             Bundle bundle = new Bundle();
                             bundle.putSerializable("BOOK_LIST", (Serializable) list);
                             progressBar.setVisibility(View.INVISIBLE);

                             fragment.setArguments(bundle);
                             loadFragment(fragment);

                         }

                         @Override
                         public void onFailure(Call<Result> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                             Log.d("----------------------", t.toString());
                         }
                     }
        );
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }


    // Nhấn back 2 lần để thoát app
    boolean canExit = false;

    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            canExit = !canExit;
        }
    }
}