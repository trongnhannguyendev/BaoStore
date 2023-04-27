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
    List<Book> bookList;
    List<Book> searchList;
    List<Category> categoryList;
    List<Publisher> publisherList;
    List<Author> authorList;
    RecyclerView recyBook_search;
    BookAdapter bookAdapter;
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
                    if (bundle.containsKey(BOOK_LIST)) {
                        bookList = (List<Book>) bundle.getSerializable(BOOK_LIST);
                    }
                    if (bundle.containsKey(CATEGORY_LIST)) {
                        categoryList = (List<Category>) bundle.getSerializable(CATEGORY_LIST);
                    }
                    if (bundle.containsKey(PUBLISHER_LIST)) {
                        publisherList = (List<Publisher>) bundle.getSerializable(PUBLISHER_LIST);
                    }
                    if (bundle.containsKey(AUTHOR_LIST)) {
                        authorList = (List<Author>) bundle.getSerializable(AUTHOR_LIST);
                        Log.d(getString(R.string.debug_frag_search), "author name: " + authorList.get(0).getAuthorname());
                    }

                    if (bundle.containsKey(BOOK_SEARCH_CODE)) {

                        searchCode = bundle.getInt(BOOK_SEARCH_CODE);
                        Log.d(getString(R.string.debug_frag_search), "Search code: "+searchCode);
                        Log.d(getString(R.string.debug_frag_search), "Book search: "+bundle.getString(BOOK_SEARCH));
                        filterV1(bundle.getString(BOOK_SEARCH));
                        spnFind.setSelection(1,false);

                    } else{
                        searchCode = 0;
                        filterV1("");
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
                        filterV1(svSearch_search.getQuery().toString());
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



                bookAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
                bookAdapter = new BookAdapter(bookList, getContext());
                recyBook_search.setAdapter(bookAdapter);
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
        if (searchList.isEmpty() && searchCode != 0){
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);

        }

        if (!searchList.isEmpty()){
            sortBy();
        }
    }


    public void sortBy(){
        long sortCode = spnSortBy.getSelectedItemPosition();
        Log.d(getString(R.string.debug_frag_search), "sortBy option: " + sortCode);
        if (searchList.isEmpty()){
            searchList = bookList;
        }
        switch ((int) sortCode) {
            case 0:
                Collections.sort(searchList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getTitle().compareTo(t1.getTitle());
                    }
                });
                break;
            case 1:
                Collections.sort(searchList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return t1.getTitle().compareTo(book.getTitle());
                    }
                });
                break;


            case 2:
                Collections.sort(searchList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getPrice().compareTo(t1.getPrice());
                    }
                });
                break;
            case 3:
                Collections.sort(searchList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return t1.getPrice().compareTo(book.getPrice());
                    }
                });
                break;
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);

    }

    void filterByTitle(String find) {
        if (searchList!= null) {
            searchList.clear();
        }
        Log.d(getString(R.string.debug_frag_search), find);
        for (int i = 0; i < bookList.size(); i++) {
            Book book;
            book = bookList.get(i);
            find = find.toLowerCase();
            if (book.getTitle().toLowerCase().contains(find)) {
                searchList.add(book);

            }
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);

    }

    void filterByPrice(double find) {
        searchList.clear();
        Log.d(getString(R.string.debug_frag_search), find + "");
        for (int i = 0; i < bookList.size(); i++) {
            Book book;
            book = bookList.get(i);
            if (book.getPrice() == find) {
                searchList.add(book);
            }
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);
    }

    void filterByPriceRange(double lowest, double highest) {
        searchList.clear();
        for (int i = 0; i < bookList.size(); i++) {
            Book book;
            book = bookList.get(i);
            double minPrice = lowest;
            double maxPrice = highest;
            if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
                searchList.add(book);
            }
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);
    }

    void filterByCategory(String categoryName){

        if (!searchList.isEmpty()) {
            searchList.clear();
        }
        Log.d(getString(R.string.debug_frag_search), categoryName + "");
        //if (bundle.containsKey(BOOK_LIST)) {

        Log.d(getString(R.string.debug_frag_search), "filterByCategory: "+bookList.get(0).getTitle());
        //}
        for (int i = 0; i < bookList.size(); i++) {
            Log.d(getString(R.string.debug_frag_search), "Category: inside loop");
            Book book = bookList.get(i);
            int categoryId = book.getCategoryid();
            Log.d(getString(R.string.debug_frag_search), "Book: "+book.getTitle());
            Log.d(getString(R.string.debug_frag_search), "categoryid: " + categoryId);

            Log.d(getString(R.string.debug_frag_search), "category: "+ categoryList.get(0));
            Category category = categoryList.get(book.getCategoryid()-1);

            Log.d(getString(R.string.debug_frag_search), "category name: "+ category.getCategoryname());

            if (category.getCategoryname().toLowerCase().contains(categoryName.toLowerCase())) {
                Log.d(getString(R.string.debug_frag_search), "--found: "+category.getCategoryname().toLowerCase());
                searchList.add(book);
            }
        }
        Log.d(getString(R.string.debug_frag_search), "search list size: "+ searchList.size());
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);
    }

    void filterByPublisher(String publisherName){
        searchList.clear();

        Log.d(getString(R.string.debug_frag_search), publisherName + "");
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            Publisher publisher = publisherList.get(book.getPublisherid()-1);
            Log.d(getString(R.string.debug_frag_search), "find: " + publisherName);
            Log.d(getString(R.string.debug_frag_search), "filterByPublisherName: " + publisher.getPublishername());
            if (publisher.getPublishername().toLowerCase().contains(publisherName.toLowerCase())) {
                searchList.add(book);
            }
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);
    }
    void filterByAuthor(String authorName){
        searchList.clear();

        Log.d(getString(R.string.debug_frag_search), authorName + "");
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            Log.d(getString(R.string.debug_frag_search), "book: " + book.getTitle());
            Log.d(getString(R.string.debug_frag_search), "author list item: " + authorList.get(0).getAuthorname());
            Author author = authorList.get(book.getAuthorid()-1);
            Log.d(getString(R.string.debug_frag_search), "author name: " + authorName);
            Log.d(getString(R.string.debug_frag_search), "filter: " + author.getAuthorname());
            if (author.getAuthorname().toLowerCase().contains(authorName.toLowerCase())) {
                searchList.add(book);
            }
        }
        bookAdapter = new BookAdapter(searchList, getContext());
        recyBook_search.setAdapter(bookAdapter);
    }



}