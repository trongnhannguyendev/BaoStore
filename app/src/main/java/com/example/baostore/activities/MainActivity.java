package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.AUTHOR_LIST;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH_CODE;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.PUBLISHER_LIST;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Api.RetrofitCallBack.bookGetAll;
import static com.example.baostore.Api.RetrofitCallBack.cartGetAllByUserID;
import static com.example.baostore.Api.RetrofitCallBack.categoryGetAll;
import static com.example.baostore.Api.RetrofitCallBack.getAuthor;
import static com.example.baostore.Api.RetrofitCallBack.getPublisher;
import static com.example.baostore.Api.RetrofitCallBack.userAddressGetAll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.fragments.HomeFragment;
import com.example.baostore.fragments.ProfileFragment;
import com.example.baostore.fragments.SearchFragment;
import com.example.baostore.models.User;
import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.AuthorResponse;
import com.example.baostore.responses.BookResponse;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.responses.CategoryResponse;
import com.example.baostore.responses.PublisherResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
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
    ApiService service;
    boolean canExit = false;

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
        service = GetRetrofit.getInstance().createRetrofit();

        User user = SharedPrefManager.getInstance(this).getUser();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_ID, user.getUserid());

        Call<BookResponse> bookResponseCall = service.getbook();
        Call<CategoryResponse> categoryResponseCall = service.getCategories();
        Call<AddressResponse> addressResponseCall = service.getAddressByUser(jsonObject);
        Call<CartResponse> cartResponseCall = service.getCartByUserID(jsonObject);
        Call<PublisherResponse> publisherResponseCall = service.getPublishers();
        Call<AuthorResponse> authorResponseCall = service.getAuthors();

        bookResponseCall.clone().enqueue(bookGetAll(this, bundle, fragment));
        categoryResponseCall.clone().enqueue(categoryGetAll(this, bundle, fragment));
        addressResponseCall.clone().enqueue(userAddressGetAll(this, bundle, fragment));
        //cartResponseCall.clone().enqueue(cartGetAllByUserID(this, bundle,fragment));
        publisherResponseCall.clone().enqueue(getPublisher(this, bundle, fragment));
        authorResponseCall.clone().enqueue(getAuthor(this, bundle, fragment));



        // Title toolbar


        //Điều hướng navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            progressBar.setVisibility(View.VISIBLE);
            switch (item.getItemId()) {
                case R.id.Home:
                    fragment = new HomeFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);

                    return true;
                case R.id.Search:
                    tvTitleHeader.setText("Tìm kiếm");
                    fragment = new SearchFragment();
                    fragment.setArguments(bundle);
                    loadSearchFragment(fragment,0,null);

                    progressBar.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.Cart:
                    tvTitleHeader.setText("Giỏ hàng");
                    fragment = new CartFragment();
                    fragment.setArguments(bundle);
                    cartResponseCall.clone().enqueue(cartGetAllByUserID(this, bundle, fragment));


                    return true;
                case R.id.User:
                    tvTitleHeader.setText("Người dùng");
                    fragment = new ProfileFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
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


    public void openActivityForResult(Intent intent){
        myResultLauncher.launch(intent);
    }

    protected ActivityResultLauncher myResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("--", "onActivityResult: "+result.getResultCode());
                    if(result.getResultCode()==111){
                        Intent data = result.getData();
                        Fragment fragment1 = new CartFragment();
                        loadFragment(fragment1);
                        bottomNavigationView.setSelectedItemId(R.id.Cart);
                    }
                }
            }
    );

    public void createSnackbar(View v,String msg){
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setAnchorView(bottomNavigationView);
        snackbar.show();
    }

    public void createToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    // Nhấn back 2 lần để thoát app
    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
        } else {
            createToast("Press again to exit");
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