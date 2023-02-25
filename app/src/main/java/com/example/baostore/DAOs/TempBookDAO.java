package com.example.baostore.DAOs;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.BookResult;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.models.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempBookDAO {
    BookAdapter adapter;
    Book2Adapter book2Adapter;
    private Context context;
    Fragment fragment;
    RecyclerView rcv1, rcv2;
    List<Book> list_book = new ArrayList<>();

    public TempBookDAO(Context context, Fragment fragment, RecyclerView rcv1, RecyclerView rcv2){
        this.context = context;
        this.fragment = fragment;
        this.rcv1 = rcv1;
        this.rcv2 = rcv2;
    }

    public void getBooks() {
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

                adapter = new BookAdapter(list_book,context);
                book2Adapter = new Book2Adapter(list_book,context);

                rcv1.setAdapter(adapter);
                rcv2.setAdapter(book2Adapter);

                adapter.notifyDataSetChanged();
                book2Adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
            }


        });
    }
}
