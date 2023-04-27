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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.models.Author;
import com.example.baostore.models.Book;
import com.example.baostore.models.Category;
import com.example.baostore.models.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchFragment extends Fragment {
    RelativeLayout rlSearchOption;
    LinearLayout llRecy;
    SearchView svSearch_search;
    Spinner spnFind, spnSortBy;
    TextView tvEmpty, tvToggle;
    List<Book> list_book;
    List<Book> searchList;
    List<Category> categoryList;
    List<Publisher> publisherList;
    List<Author> authorList;
    RecyclerView recyBook_search;
    BookAdapter adapter;
    int searchCode = 0;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        try {
            if (getContext() != null && isAdded()) {
                svSearch_search = v.findViewById(R.id.svSearch_search);
                recyBook_search = v.findViewById(R.id.recyBook_search);
                spnFind = v.findViewById(R.id.spnFind_search);
                spnSortBy = v.findViewById(R.id.spnSortBy_search);
                tvEmpty = v.findViewById(R.id.tvEmptyMsg_search);
                tvToggle = v.findViewById(R.id.toggleAction);
                rlSearchOption = v.findViewById(R.id.llSearchOptions);
                llRecy = v.findViewById(R.id.llRecy_fs);

                recyBook_search.setLayoutManager(new LinearLayoutManager(getContext()));

                searchList = new ArrayList<>();

                bundle = getArguments();
                if (bundle != null) {
                    if (bundle.containsKey(BOOK_SEARCH_CODE)) {
                        Toast.makeText(getContext(), "In", Toast.LENGTH_SHORT).show();
                        searchCode = bundle.getInt(BOOK_SEARCH_CODE);
                        filterV1(bundle.getString(BOOK_SEARCH));
                    } else{
                        searchCode = 0;
                    }
                    if (bundle.containsKey(BOOK_LIST)) {
                        list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);
                    }
                    if (bundle.containsKey(CATEGORY_LIST)) {
                        categoryList = (List<Category>) bundle.getSerializable(CATEGORY_LIST);
                    }
                    if (bundle.containsKey(PUBLISHER_LIST)) {
                        publisherList = (List<Publisher>) bundle.getSerializable(PUBLISHER_LIST);
                    }
                    if (bundle.containsKey(AUTHOR_LIST)) {
                        authorList = (List<Author>) bundle.getSerializable(AUTHOR_LIST);
                    }

                }

                ArrayAdapter spnAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_item));
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnFind.setAdapter(spnAdapter);

                ArrayAdapter spnAdapterSort = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sort_item));
                spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnSortBy.setAdapter(spnAdapterSort);


                spnFind.setSelection(0, false);
                spnFind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        searchCode = i + 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                spnSortBy.setSelection(0, false);
                spnSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sortBy();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                adapter = new BookAdapter(list_book, getContext());
                recyBook_search.setAdapter(adapter);


                svSearch_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        filterV1(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (searchCode == 0) {
                            searchCode = 1;
                        }
                        filterV1(newText);
                        return false;
                    }
                });

                adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                    }
                });

                return v;
            }
        } catch (Exception e){
            Toast.makeText(getContext(), getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_frag_search), "Error: "+e);
        }
        return v;

    }

    public void filterV1 (String findWhat) {
        Log.d(getString(R.string.debug_frag_search), "Search code: " + searchCode);
        switch (searchCode) {
            // All
            case 0:
                adapter = new BookAdapter(list_book, getContext());
                recyBook_search.setAdapter(adapter);
                break;
                // Title
            case 1:
                String title =findWhat;
                filterByTitle(title);
                break;
                //Category
            case 2:
                String categoryName = findWhat;
                filterByCategory(categoryName);
                break;
                // Author
            case 3:
                String authorName = findWhat;
                filterByAuthor(authorName);
                break;
                // Publisher
            case 4:
                String publisherName = findWhat;
                filterByPublisher(publisherName);
                break;
            case 5:
                double price = Double.parseDouble(findWhat);
                filterByPrice(price);
            case 6:
                String categoryFromHome = bundle.getString(BOOK_SEARCH);
                filterByCategory(categoryFromHome);
                break;
            case 7:
                String titleFromHome =bundle.getString(BOOK_SEARCH);
                filterByTitle(titleFromHome);
                break;

        }
        if (searchList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
        } else if(tvEmpty.getVisibility() == View.VISIBLE){
            tvEmpty.setVisibility(View.GONE);
        }
    }


    public void sortBy(){
        long sortCode = spnSortBy.getSelectedItemPosition();
        Log.d(getString(R.string.debug_frag_search), "sortBy option: " + sortCode);
        if (searchList.size() ==0){
            searchList = list_book;
        }
        if(sortCode == 0) {
            Collections.sort(searchList, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return book.getTitle().compareTo(t1.getTitle());
                }
            });
        }

        if (sortCode == 1){
            Collections.sort(searchList, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return t1.getTitle().compareTo(book.getTitle());
                }
            });
        }

        if (sortCode == 2){
            Collections.sort(searchList, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return book.getPrice().compareTo(t1.getPrice());
                }
            });
        }
        if (sortCode == 3){
            Collections.sort(searchList, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return t1.getPrice().compareTo(book.getPrice());
                }
            });
        }
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

    }

    void filterByTitle(String find) {
        if (searchList!= null) {
            searchList.clear();
        }
        Log.d(getString(R.string.debug_frag_search), find);
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            find = find.toLowerCase();
            if (book.getTitle().toLowerCase().contains(find)) {
                searchList.add(book);

            }
        }
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);

    }

    void filterByPrice(double find) {
        searchList.clear();
        Log.d(getString(R.string.debug_frag_search), find + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book;
            book = list_book.get(i);
            if (book.getPrice() == find) {
                searchList.add(book);
            }
        }
        adapter = new BookAdapter(searchList, getContext());
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
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }

    void filterByCategory(String categoryName){
        if (!searchList.isEmpty()) {
            searchList.clear();
        }
        Log.d("----------------------", categoryName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book = list_book.get(i);
            Log.d(getString(R.string.debug_frag_search), "categoryid: " + book.getCategoryid());
            Category category = categoryList.get(book.getCategoryid()-1);


            Log.d(getString(R.string.debug_frag_search), "Find: " + categoryName);
            Log.d(getString(R.string.debug_frag_search), "filterByCategoryName: " + category.getCategoryname());
            if (category.getCategoryname().toLowerCase().contains(categoryName.toLowerCase())) {
                Log.d(getString(R.string.debug_frag_search), "--found: "+category.getCategoryname().toLowerCase());
                searchList.add(book);
            }
        }
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }

    void filterByPublisher(String publisherName){
        if (searchList != null) {
            searchList.clear();
        }

        Log.d(getString(R.string.debug_frag_search), publisherName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book = list_book.get(i);
            Publisher publisher = publisherList.get(book.getPublisherid()-1);
            Log.d(getString(R.string.debug_frag_search), "find: " + publisherName);
            Log.d(getString(R.string.debug_frag_search), "filterByPublisherName: " + publisher.getPublishername());
            if (publisher.getPublishername().toLowerCase().contains(publisherName.toLowerCase())) {
                searchList.add(book);
            }
        }
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }
    void filterByAuthor(String authorName){
        if (searchList != null) {
            searchList.clear();
        }

        Log.d("----------------------", authorName + "");
        for (int i = 0; i < list_book.size(); i++) {
            Book book = list_book.get(i);
            Author author = authorList.get(book.getAuthorid()-1);
            Log.d(getString(R.string.debug_frag_search), "author name: " + authorName);
            Log.d(getString(R.string.debug_frag_search), "filter: " + author.getAuthorname());
            if (author.getAuthorname().toLowerCase().contains(authorName.toLowerCase())) {
                searchList.add(book);
            }
        }
        adapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(adapter);
    }



}