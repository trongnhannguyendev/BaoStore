package com.example.baostore.fragments;




import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.DAOs.TempBookDAO;
import com.example.baostore.DAOs.TempCategoryDAO;
import com.example.baostore.R;

import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.adapters.CategoryAdapter;
import com.example.baostore.models.Book;
import com.example.baostore.models.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    List<Book> list_book;
    List<Category> list_category;
    RecyclerView recyBook_Popular, recyBook_New, recyCategory;
    BookAdapter adapter;
    Book2Adapter book2Adapter;
    CategoryAdapter categoryAdapter;
    TempBookDAO tempBookDAO;
    TempCategoryDAO tempCategoryDAO;
    LinearLayout btnSearchNew, btnSearchPopular;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home, container, false);

        list_book = new ArrayList<>();
        list_category= new ArrayList<>();

        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_New = v.findViewById(R.id.recyBook_new);
        recyCategory = v.findViewById(R.id.recyCategory);
        btnSearchNew = v.findViewById(R.id.btnSearchNew);
        btnSearchPopular = v.findViewById(R.id.btnSearchPopular);

        tempBookDAO = new TempBookDAO(getContext(),this,adapter,book2Adapter);
        tempCategoryDAO = new TempCategoryDAO(getContext(), recyCategory);

        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyBook_New.setLayoutManager(new LinearLayoutManager(getContext()));
        recyCategory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getBooks();
        getCategory();

        adapter = new BookAdapter(list_book,getContext());
        book2Adapter = new Book2Adapter(list_book,getContext());
        categoryAdapter = new CategoryAdapter(list_category,getContext());

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);
        recyCategory.setAdapter(categoryAdapter);



        tempCategoryDAO.getCategory();

        btnSearchNew.setOnClickListener(view->{
            Fragment fragment = new SearchFragment();
            loadFragment(fragment);
        });

        btnSearchPopular.setOnClickListener(view ->{
            Fragment fragment = new SearchFragment();
            loadFragment(fragment);
        });


        return v;
    }

    public void loadFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }




/*
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        NestedScrollView nestedScrollView = view.findViewById(R.id.myScrollView);
        LinearLayout layout1 = view.findViewById(R.id.layout_category_1);
        LinearLayout layout2 = view.findViewById(R.id.layout_category_2);





        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
                layout2.getLocationOnScreen(location);
//                int[] layoutLocation = new int[2];
//                layout2.getLocationOnScreen(layoutLocation);
//
//                int[] scrollViewLocation = new int[2];
//                nestedScrollView.getLocationOnScreen(scrollViewLocation);
//
//                int distanceFromLayoutTopToScrollViewTop = layoutLocation[1] - scrollViewLocation[1];



                int y = location[1]-layout2.getHeight()-93;
                Log.i("TAG",  "Y: "+y);
                Log.i("TAG", "Show + "+scrollY);
                if (y<=0){
                    layout1.setVisibility(View.VISIBLE);


                }
                else {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }
        });






        return view;


 */

    public void startRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);


        Call<Result> call = service.getCategories();
    }

    public void getBooks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);


        Call<Result> call = service.getbook();
        call.enqueue(new Callback<Result>() {
                         @Override
                         public void onResponse(Call<Result> call, Response<Result> response) {
                             JsonElement element = response.body().getData();
                             JsonArray myArr = element.getAsJsonArray();

                             Log.d("------------------------", myArr.size()+"");
                             for(JsonElement jsonElement: myArr){
                                 JsonObject jsonObject = jsonElement.getAsJsonObject();
                                 int bookID = jsonObject.get("bookid").getAsInt();
                                 String title = jsonObject.get("title").getAsString();
                                 double price = jsonObject.get("price").getAsDouble();
                                 String url = jsonObject.get("url").getAsString();

                                 Book book = new Book(bookID,title, price,url);
                                 Log.d("--------------------",book.getTitle());
                                 list_book.add(book);

                             }


                             adapter.notifyDataSetChanged();
                             book2Adapter.notifyDataSetChanged();


                         }

                         @Override
                         public void onFailure(Call<Result> call, Throwable t) {
                             Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_LONG).show();
                             Log.d("----------------------",t.toString());
                         }
                     }
        );
}

    public void getCategory(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);


        Call<Result> call = service.getCategories();


        call.enqueue(new Callback<Result>() {
                         @Override
                         public void onResponse(Call<Result> call, Response<Result> response) {
                             JsonElement element = response.body().getData();
                             JsonArray myArr = element.getAsJsonArray();


                             Log.d("------------------------", myArr.size()+"");
                             for(JsonElement jsonElement: myArr){
                                 JsonObject jsonObject = jsonElement.getAsJsonObject();
                                 int categoryID = jsonObject.get("categoryID").getAsInt();
                                 String categoryName = jsonObject.get("categoryName").getAsString();

                                 Category category = new Category(categoryID,categoryName);
                                 Log.d("--------------------",category.getCategoryName());
                                 list_category.add(category);

                             }

                             categoryAdapter = new CategoryAdapter(list_category,getContext());
                             recyCategory.setAdapter(categoryAdapter);

                             categoryAdapter.notifyDataSetChanged();


                         }

                         @Override
                         public void onFailure(Call<Result> call, Throwable t) {
                             Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_LONG).show();
                             Log.d("----------------------",t.toString());
                         }
                     }
        );
    }
    }
