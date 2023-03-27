package com.example.baostore.fragments;


import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.adapters.CategoryAdapter;
import com.example.baostore.models.Book;
import com.example.baostore.models.Category;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    LinearLayout btnSearchNew, btnSearchPopular, btnSearch_home;
    EditText edSearch;
    ImageView ivSearch;
    RecyclerView recyBook_Popular, recyBook_New, recyCategory, recyCategory1;
    BookAdapter adapter;
    Book2Adapter book2Adapter;
    CategoryAdapter categoryAdapter;
    MainActivity activity;
    Bundle bundle;
    List<Book> list_book;
    List<Category> list_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        NestedScrollView nestedScrollView = v.findViewById(R.id.myScrollView);
        LinearLayout layout1 = v.findViewById(R.id.layout_category_1);
        LinearLayout layout2 = v.findViewById(R.id.layout_category_2);

        list_book = new ArrayList<>();
        list_category = new ArrayList<>();

        // Ánh xạ
        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_New = v.findViewById(R.id.recyBook_new);
        recyCategory = v.findViewById(R.id.recyCategory);
        recyCategory1 = v.findViewById(R.id.recyCategory1);
        btnSearchNew = v.findViewById(R.id.btnSearchNew);
        btnSearchPopular = v.findViewById(R.id.btnSearchPopular);
        edSearch = v.findViewById(R.id.edSearch_home);
        ivSearch = v.findViewById(R.id.ibSearch_home);
        activity = (MainActivity) getContext();

        // Loại recycler view
        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyBook_New.setLayoutManager(new LinearLayoutManager(getContext()));
        recyCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyCategory1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Lấy bundle từ MainActivity
        bundle = getArguments();
        getBooks();
        getCategory();

        adapter = new BookAdapter(list_book, getContext());
        book2Adapter = new Book2Adapter(list_book, getContext());
        categoryAdapter = new CategoryAdapter(list_category, getContext());

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);
        recyCategory.setAdapter(categoryAdapter);
        recyCategory1.setAdapter(categoryAdapter);

        btnSearchNew.setOnClickListener(view -> {
            Fragment fragment = new SearchFragment();

            activity.loadFragment(fragment);
        });

        btnSearchPopular.setOnClickListener(view -> {
            Fragment fragment = new SearchFragment();
            activity.loadFragment(fragment);
        });

        ivSearch.setOnClickListener(view -> {
            String find = edSearch.getText().toString();
            Fragment fragment = new SearchFragment();
            activity.setSearchSelection();
            if (find.isEmpty()) {
                activity.loadSearchFragment(fragment, 0, null);
                Log.d("---------HomeFrag", "0");
            } else {
                activity.loadSearchFragment(fragment, 7, find);
                Log.d("---------HomeFrag", "2");
                Log.d("---------HomeFrag", "find");
            }


        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
                layout2.getLocationOnScreen(location);
//                int[] layoutLocation = new int[2];
//                layout2.getLocationOnScreen(layoutLocation);
//
//                int[] scrollViewLocation = new int[2];
//                nestedScrollView.getLocationOnScreen(scrollViewLocation);
//
//                int distanceFromLayoutTopToScrollViewTop = layoutLocation[1] - scrollViewLocation[1];


                int y = location[1] - layout2.getHeight() - 93;
                Log.i("TAG", "Y: " + y);
                Log.i("TAG", "Show + " + scrollY);
                if (y <= 0) {
                    layout1.setVisibility(View.VISIBLE);


                } else {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }
        });


        return v;
    }

    // Load list to recyclerview
    public void getBooks() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BOOK_LIST)) {
            list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);

            adapter = new BookAdapter(list_book, getContext());
            book2Adapter = new Book2Adapter(list_book, getContext());

            recyBook_Popular.setAdapter(adapter);
            recyBook_New.setAdapter(book2Adapter);

            Log.d("---------------------------HomeFrag", list_book.get(0).getTitle());
        } else {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getBooks();
                }
            }, 1000);
        }
    }

    public void getCategory() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CATEGORY_LIST)) {
            list_category = (List<Category>) bundle.getSerializable(CATEGORY_LIST);

            categoryAdapter = new CategoryAdapter(list_category, getContext());

            recyCategory.setAdapter(categoryAdapter);
            Log.d("--HomeFrag", list_category.get(0).getCategoryname());
        } else {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCategory();
                }
            }, 1000);


        }
    }


}
