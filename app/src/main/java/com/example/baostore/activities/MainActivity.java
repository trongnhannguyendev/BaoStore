package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH_CODE;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.testapi.RetrofitCallBack.bookGetAll;
import static com.example.baostore.testapi.RetrofitCallBack.cartGetAllByUserID;
import static com.example.baostore.testapi.RetrofitCallBack.categoryGetAll;
import static com.example.baostore.testapi.RetrofitCallBack.userAddressGetAll;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.fragments.HomeFragment;
import com.example.baostore.fragments.ProfileFragment;
import com.example.baostore.fragments.SearchFragment;
import com.example.baostore.models.User;
import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.BookResponse;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.responses.CategoryResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
    ImageView imgBack;
    TextView tvTitleHeader;
    Fragment fragment;
    ProgressBar progressBar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = new Bundle();
        bundle.putInt(BOOK_SEARCH_CODE, 0);

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
        ApiService service = new GetRetrofit().getRetrofit();

        User user = SharedPrefManager.getInstance(this).getUser();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_ID, user.getUserid());


        Call<BookResponse> bookResponseCall = service.getbook();
        Call<CategoryResponse> categoryResponseCall = service.getCategories();
        Call<AddressResponse> addressResponseCall = service.getAddressByUser(jsonObject);
        Call<CartResponse> cartResponseCall = service.getCartByUserID(jsonObject);

        bookResponseCall.clone().enqueue(bookGetAll(this, bundle, fragment));
        categoryResponseCall.clone().enqueue(categoryGetAll(this, bundle, fragment));
        addressResponseCall.clone().enqueue(userAddressGetAll(this, bundle, fragment));
        //cartResponseCall.clone().enqueue(cartGetAllByUserID(this, bundle,fragment));



        // Title toolbar


        //Điều hướng navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    progressBar.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    if(bundle == null || !bundle.containsKey(CATEGORY_LIST)){
                        categoryResponseCall.enqueue(categoryGetAll(this, bundle, fragment));
                        bookResponseCall.enqueue(bookGetAll(this, bundle, fragment));
                    } else{
                        fragment.setArguments(bundle);
                        loadFragment(fragment);
                    }
                    return true;
                case R.id.Search:
                    progressBar.setVisibility(View.VISIBLE);
                    tvTitleHeader.setText("Tìm kiếm");
                    fragment = new SearchFragment();
                    if(bundle == null || !bundle.containsKey(BOOK_LIST)){
                        bookResponseCall.enqueue(bookGetAll(this, bundle, fragment));
                    } else{
                        fragment.setArguments(bundle);
                        loadSearchFragment(fragment,0,null);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.Cart:
                    progressBar.setVisibility(View.VISIBLE);
                    tvTitleHeader.setText("Giỏ hàng");

                    fragment = new CartFragment();
                    cartResponseCall.clone().enqueue(cartGetAllByUserID(this, bundle, fragment));

                    if(!bundle.containsKey(ADDRESS_LIST)){
                        Log.d("--", "No address list");
                        addressResponseCall.enqueue(userAddressGetAll(this, bundle, fragment));
                    }
                    return true;
                case R.id.User:
                    progressBar.setVisibility(View.VISIBLE);
                    tvTitleHeader.setText("Người dùng");
                    fragment = new ProfileFragment();
                    if(!bundle.containsKey(ADDRESS_LIST)) {
                        addressResponseCall.enqueue(userAddressGetAll(this, bundle, fragment));
                    }
                    else{
                        fragment.setArguments(bundle);
                        loadFragment(fragment);
                    }
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

    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void loadSearchFragment(Fragment fragment, int searchCode, String find) {

        bundle.putInt(BOOK_SEARCH_CODE, searchCode);
        bundle.putString(BOOK_SEARCH, find);
        fragment.setArguments(bundle);

        Log.d(String.valueOf(R.string.debug_MainActivity), "Search code: "+ searchCode);
        loadFragment(fragment);
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void setSearchSelection(){
        bottomNavigationView.setSelectedItemId(R.id.Search);
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

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    canExit = false;
                }
            }, 1000);
        }


    }
}