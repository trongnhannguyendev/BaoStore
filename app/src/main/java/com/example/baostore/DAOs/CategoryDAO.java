package com.example.baostore.DAOs;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.adapters.CategoryAdapter;
import com.example.baostore.models.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    Context context;
    CategoryAdapter adapter;
    RecyclerView recy;
    List<Category> list_category = new ArrayList<>();

    public CategoryDAO(Context context){
        this.context = context;
    }

    public List<Category> getData(JsonArray array){
        List<Category> list = new ArrayList<>();
        for (JsonElement jsonElement : array) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int categoryID = jsonObject.get("categoryID").getAsInt();
            String categoryName = jsonObject.get("categoryName").getAsString();
            Category category = new Category(categoryID, categoryName);
            list.add(category);
            Log.d("-------------adapter", categoryName);
        }
        return list;

    }


}
