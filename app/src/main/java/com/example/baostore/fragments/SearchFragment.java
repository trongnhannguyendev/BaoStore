package com.example.baostore.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.DAOs.BookDAO;
import com.example.baostore.R;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.models.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    SearchView svSearch_search;
    List<Book> list_book;
    List<Book> searchList;
    RecyclerView recyBook_search;
    Book2Adapter adapter;
    BookDAO dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        svSearch_search = v.findViewById(R.id.svSearch_search);
        recyBook_search = v.findViewById(R.id.recyBook_search);
        recyBook_search.setLayoutManager(new LinearLayoutManager(getContext()));

        dao = new BookDAO(getContext());
        list_book = new ArrayList<>();
        searchList = new ArrayList<>();

        getBooks();
        svSearch_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return v;


    }

    void filter(String find){
        searchList.clear();
        Log.d("----------------------",find);
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            find = find.toLowerCase();
            if(book.getTitle().toLowerCase().contains(find)){
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList,getContext());
        recyBook_search.setAdapter(adapter);

    }

    public void getBooks() {
        ApiService service = new GetRetrofit().getRetrofit();


        Call<Result> call = service.getbook();
        call.enqueue(new Callback<Result>() {
                         @Override
                         public void onResponse(Call<Result> call, Response<Result> response) {
                             int responseCode = response.body().getResponseCode();
                             if (responseCode == 1) {
                                 JsonElement element = response.body().getData();
                                 JsonArray myArr = element.getAsJsonArray();

                                 list_book = dao.getData(myArr);

                                 adapter = new Book2Adapter(list_book, getContext());

                                 recyBook_search.setAdapter(adapter);

                                 adapter.notifyDataSetChanged();

                             } else{
                                 Toast.makeText(getContext(), "Something wrong happen", Toast.LENGTH_SHORT).show();
                                 Log.d("-----------------------------SearchFragment", response.body().getMessage());
                             }
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