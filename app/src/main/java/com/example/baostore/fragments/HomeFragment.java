package com.example.baostore.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.BookResult;
import com.example.baostore.Api.Result;
import com.example.baostore.R;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    List<Book> list_book;
    RecyclerView recyBook_Popular;
    BookAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home, container, false);
        list_book = new ArrayList<>();
        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new BookAdapter(list_book,getContext());
        recyBook_Popular.setAdapter(adapter);

        getBooks();


        return v;
    }

    private void getBooks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<BookResult> call = service.getbook();
        call.enqueue(new Callback<BookResult>() {
            @Override
            public void onResponse(Call<BookResult> call, Response<BookResult> response) {
                list_book = response.body().getBook();
                adapter = new BookAdapter(list_book,getContext());
                recyBook_Popular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }


        });
    }
}