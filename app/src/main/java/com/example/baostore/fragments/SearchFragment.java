package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.AUTHOR_LIST;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH;
import static com.example.baostore.Constant.Constants.BOOK_SEARCH_CODE;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.PUBLISHER_LIST;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.models.Author;
import com.example.baostore.models.Book;
import com.example.baostore.models.Category;
import com.example.baostore.models.Publisher;

import java.util.List;


public class SearchFragment extends Fragment {
    SearchView svSearch_search;
    // TODO add list publisher, list author
    List<Book> list_book;
    List<Book> searchList;
    List<Category> categoryList;
    List<Publisher> publisherList;
    List<Author> authorList;
    RecyclerView recyBook_search;
    Book2Adapter adapter;
    int searchCode = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        svSearch_search = v.findViewById(R.id.svSearch_search);
        recyBook_search = v.findViewById(R.id.recyBook_search);
        recyBook_search.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        if(bundle != null) {
            list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);
            categoryList = (List<Category>) bundle.getSerializable(CATEGORY_LIST);
            publisherList = (List<Publisher>) bundle.getSerializable(PUBLISHER_LIST);
            authorList = (List<Author>) bundle.getSerializable(AUTHOR_LIST);
        }

        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

        filterV1();


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

    void filter(String find, int findCode, double maxPrice) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);
            if(bundle.containsKey(CATEGORY_LIST)){
                categoryList = (List<Category>) bundle.getSerializable(CATEGORY_LIST);
            }
            switch (findCode) {
                // No filter
                case 0:
                    adapter = new Book2Adapter(list_book, getContext());
                    recyBook_search.setAdapter(adapter);
                    break;
                //Title
                case 1:
                    filterByTitle(find);
                    break;
                // Category
                case 2:
                    filterByCategoryID(Integer.parseInt(find));
                    break;
                // Price
                case 3:
                    filterByPrice(Double.parseDouble(find));
                    break;
                // Price range
                case 4:
                    filterByPriceRange(Double.parseDouble(find), maxPrice);
                    break;

            }
        }
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

    void filterByPrice(double find) {
        searchList.clear();
        Log.d("----------------------", find + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            if (book.getPrice() == find) {
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }

    void filterByPriceRange(double lowest, double highest) {
        searchList.clear();
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            double minPrice = lowest;
            double maxPrice = highest;
            if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }

    void filterByCategoryID(int categoryID) {
        searchList.clear();
        Log.d("----------------------", categoryID + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            Log.d("---", "filterByCategoryID: " + categoryID);
            Log.d("---", "filterByCategoryID: " + book.getCategoryid());
            if (book.getCategoryid() == categoryID) {
                searchList.add(book);


            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

    }

    void filterByCategory(String categoryName){
        searchList.clear();
        Log.d("----------------------", categoryName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book = list_book.get(i);
            Category category = categoryList.get(book.getCategoryid());


            Log.d("---", "filterByCategoryName: " + categoryName);
            Log.d("---", "filterByCategoryName: " + book.getCategoryid());
            if (category.getCategoryname() == categoryName) {
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }

    void filterByPublisher(String publisherName){
        searchList.clear();

        Log.d("----------------------", publisherName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Publisher publisher = publisherList.get(i);
            Book book = list_book.get(i);
            publisher = publisherList.get(book.getPublisherid());
            Log.d("---", "filterByPublisherName: " + publisherName);
            Log.d("---", "filterByPublisherName: " + publisher.getPublishername());
            if (publisher.getPublishername() == publisherName) {
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }
    void filterByAuthor(String authorName){
        searchList.clear();

        Log.d("----------------------", authorName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book = list_book.get(i);
            Author author = authorList.get(book.getbookid());
            Log.d("---", "filterByAuthorName: " + authorName);
            Log.d("---", "filterByAuthorName: " + author.getAuthorname());
            if (author.getAuthorname() == authorName) {
                searchList.add(book);
            }
        }
        adapter = new Book2Adapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }


    public void filterV1 () {
        Bundle bundle = getArguments();

            // TODO: add find string
            String find1 = "";
                Log.d("---------------------------HomeFrag", list_book.get(0).getTitle());
                searchCode = bundle.getInt(BOOK_SEARCH_CODE);
                Log.d("------------------SearchFragment", "Search code: " + searchCode);
                switch (searchCode) {
                    case 0:
                        adapter = new Book2Adapter(list_book, getContext());
                        recyBook_search.setAdapter(adapter);
                        break;
                    case 1:
                        int categoryID = Integer.parseInt(bundle.getString(BOOK_SEARCH));

                        Log.d("------------------SearchFragment", categoryID + "");
                        filterByCategoryID(categoryID);
                        break;
                    case 2:
                        String find = bundle.getString(BOOK_SEARCH);
                        filterByTitle(find);
                        break;
                    case 3:
                        String categoryName = find1;
                        filterByCategory(categoryName);
                        break;
                    case 4:
                        String publisherName = find1;
                        filterByPublisher(publisherName);
                        break;
                    case 5:
                        String authorName = find1;
                        filterByAuthor(authorName);


        }
    }
}