package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH_CODE;
import static com.example.baostore.Constant.Constants.CATEGORY_ID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.DAOs.BookDAO;
import com.example.baostore.R;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.models.Book;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    SearchView svSearch_search;
    List<Book> list_book;
    List<Book> searchList;
    RecyclerView recyBook_search;
    Book2Adapter adapter;
    BookDAO dao;
    int searchCode = 0;

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

        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

        getBooks();



        svSearch_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterByTitle(newText);
                return false;
            }
        });

        return v;


    }

    void filterByTitle(String find) {
        searchList.clear();
        Log.d("----------------------", find);
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            find = find.toLowerCase();
            if (book.getTitle().toLowerCase().contains(find)) {
                searchList.add(book);

            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

    }

    void filterByCategory(int categoryID){
        searchList.clear();
        Log.d("----------------------", categoryID+"");
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            if (book.getCategoryID() == categoryID) {
                searchList.add(book);

            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

    }

    public void getBooks() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);

            Log.d("---------------------------HomeFrag", list_book.get(0).getTitle());

            searchCode = bundle.getInt(BOOK_SEARCH_CODE);
            Log.d("------------------SearchFragment", searchCode+"");
            switch (searchCode) {
                case 0:
                    adapter = new Book2Adapter(list_book, getContext());
                    recyBook_search.setAdapter(adapter);
                    break;
                case 1:
                    int categoryID = Integer.parseInt(bundle.getString(BOOK_SEARCH));

                    Log.d("------------------SearchFragment", categoryID+"");
                    filterByCategory(categoryID);
                    break;
                case 2:
                    String find = bundle.getString(BOOK_SEARCH);
                    filterByTitle(find);


            }

        }
    }
}