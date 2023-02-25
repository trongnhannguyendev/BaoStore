package com.example.baostore.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.BookResult;
import com.example.baostore.DAOs.TempBookDAO;
import com.example.baostore.R;
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


public class HomeFragment extends Fragment {
    List<Book> list_book;
    RecyclerView recyBook_Popular, recyBook_New;
    BookAdapter adapter;
    Book2Adapter book2Adapter;
    TempBookDAO tempBookDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home, container, false);

        list_book = new ArrayList<>();

        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_New = v.findViewById(R.id.recyBook_new);

        tempBookDAO = new TempBookDAO(getContext(),this,recyBook_Popular,recyBook_New);

        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyBook_New.setLayoutManager(new LinearLayoutManager(getContext()));



        tempBookDAO.getBooks();

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);

        return v;
    }

    public void showBooks(){
        //list_book = bookDAO.getBooks();
        adapter = new BookAdapter(list_book,getContext());
        book2Adapter = new Book2Adapter(list_book,getContext());

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);

        adapter.notifyDataSetChanged();
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
                Log.d("-----------------------fragment",list_book.get(0).getTitle());


                adapter = new BookAdapter(list_book,getContext());
                book2Adapter = new Book2Adapter(list_book,getContext());

                recyBook_Popular.setAdapter(adapter);
                recyBook_New.setAdapter(book2Adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }


        });
    }
}