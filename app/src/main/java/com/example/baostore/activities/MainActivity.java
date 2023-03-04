package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH_CODE;
import static com.example.baostore.Constant.Constants.CATEGORY_ID;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_ID;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.DAOs.BookDAO;
import com.example.baostore.DAOs.CategoryDAO;
import com.example.baostore.R;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.fragments.HomeFragment;
import com.example.baostore.fragments.ProfileFragment;
import com.example.baostore.fragments.SearchFragment;
import com.example.baostore.models.Address;
import com.example.baostore.models.Book;
import com.example.baostore.models.Category;
import com.example.baostore.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    CategoryDAO categoryDAO;
    Fragment fragment;
    ProgressBar progressBar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new BookDAO(this);
        categoryDAO = new CategoryDAO(this);
        bundle = new Bundle();
        bundle.putInt(BOOK_SEARCH_CODE, 0);

        progressBar = findViewById(R.id.progressBar_Main);

        loadUserAddresses();

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
        loadCategoryToFragment(fragment);
        loadBooksToFragment(fragment);


        // Title toolbar


        //Điều hướng navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    progressBar.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    if(bundle == null){
                        loadCategoryToFragment(fragment);
                        loadBooksToFragment(fragment);
                    } else{
                        fragment.setArguments(bundle);
                        loadFragment(fragment);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    return true;
                case R.id.Search:
                    tvTitleHeader.setText("Tìm kiếm");
                    fragment = new SearchFragment();
                    if(bundle == null){
                        loadBooksToFragment(fragment);
                    } else{
                        fragment.setArguments(bundle);
                        loadSearchFragment(fragment,0,0);
                    }
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

                             List<Book> list = dao.getData(myArr);

                             bundle.putSerializable(BOOK_LIST, (Serializable) list);
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

    public void loadCategoryToFragment(Fragment fragment){
        ApiService service = new GetRetrofit().getRetrofit();
        Call<Result> call = service.getCategories();


        call.enqueue(new Callback<Result>() {
                         @Override
                         public void onResponse(Call<Result> call, Response<Result> response) {
                             int responseCode = response.body().getResponseCode();
                             if (responseCode == 1) {
                                 JsonElement element = response.body().getData();
                                 JsonArray myArr = element.getAsJsonArray();
                                 List<Category> categoryList = categoryDAO.getData(myArr);

                                 Log.d("-----------------Main", categoryList.get(0).getCategoryName());
                                 bundle.putSerializable(CATEGORY_LIST, (Serializable) categoryList);

                                 fragment.setArguments(bundle);
                                 loadFragment(fragment);

                             }


                         }

                         @Override
                         public void onFailure(Call<Result> call, Throwable t) {
                             Toast.makeText(MainActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
                             Log.d("----------------------", t.toString());
                         }
                     }
        );
    }

    public void loadUserAddresses(){
        ApiService service = new GetRetrofit().getRetrofit();
        User user = SharedPrefManager.getInstance(this).getUser();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_ID, user.getUserID());

        Call<Result> call = service.getAddressByUser(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.body().getResponseCode() == RESPONSE_OKAY){
                    JsonElement element = response.body().getData();
                    JsonArray array = element.getAsJsonArray();
                    JsonObject object = array.get(0).getAsJsonObject();
                    Address address = new Address();
                    address.setAddressLocation(object.get(ADDDRESS_LOCATION).getAsString());

                    SharedPrefManager.getInstance(MainActivity.this).saveUserAddressList(address);
                    Log.d("--------MainActivity", object.get(ADDDRESS_LOCATION).getAsString());
                } else{
                    Toast.makeText(MainActivity.this, "Error found", Toast.LENGTH_SHORT).show();
                    Log.d("------MainActivity", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("-------------------------MainActivity", t.toString());
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    public void loadSearchFragment(Fragment fragment, int searchCode, int categoryID) {

        bundle.putInt(BOOK_SEARCH_CODE, searchCode);
        bundle.putInt(CATEGORY_ID, categoryID);
        fragment.setArguments(bundle);

        Log.d("--------------------MAIN", searchCode+"");
        Log.d("--------------------MAIN", categoryID+"");
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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