package com.example.baostore.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.baostore.R;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.fragments.HomeFragment;
import com.example.baostore.fragments.ProfileFragment;
import com.example.baostore.fragments.SearchFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
     ImageView imgBack;
     TextView tvTitleHeader;
     Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();

        // Title toolbar


        //Điều hướng navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:

                    tvTitleHeader.setText("Trang chủ");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
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


}