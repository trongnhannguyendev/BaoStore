package com.example.baostore.DAOs;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.adapters.CategoryAdapter;
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

public class TempCategoryDAO {
    Context context;
    CategoryAdapter adapter;
    RecyclerView recy;
    List<Category> list_category = new ArrayList<>();

    public TempCategoryDAO(Context context, RecyclerView recy) {
        this.context = context;
        this.recy = recy;
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

                             adapter = new CategoryAdapter(list_category,context);
                             recy.setAdapter(adapter);

                             adapter.notifyDataSetChanged();


                         }

                         @Override
                         public void onFailure(Call<Result> call, Throwable t) {
                             Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
                             Log.d("----------------------",t.toString());
                         }
                     }
        );
    }
}
