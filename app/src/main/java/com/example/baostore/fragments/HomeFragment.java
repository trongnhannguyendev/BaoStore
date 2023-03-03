package com.example.baostore.fragments;


import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.DAOs.CategoryDAO;
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
    List<Book> list_book;
    List<Category> list_category;
    RecyclerView recyBook_Popular, recyBook_New, recyCategory;
    BookAdapter adapter;
    Book2Adapter book2Adapter;
    CategoryAdapter categoryAdapter;
    CategoryDAO categoryDAO;
    LinearLayout btnSearchNew, btnSearchPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        list_book = new ArrayList<>();
        list_category = new ArrayList<>();

        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_New = v.findViewById(R.id.recyBook_new);
        recyCategory = v.findViewById(R.id.recyCategory);
        btnSearchNew = v.findViewById(R.id.btnSearchNew);
        btnSearchPopular = v.findViewById(R.id.btnSearchPopular);

        categoryDAO = new CategoryDAO(getContext());

        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyBook_New.setLayoutManager(new LinearLayoutManager(getContext()));
        recyCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        MainActivity activity = (MainActivity) getContext();
        //activity.loadBooksToFragment(this);
        getBooks();

        //activity.loadCategoryToFragment(this);
        getCategory();

        adapter = new BookAdapter(list_book, getContext());
        book2Adapter = new Book2Adapter(list_book, getContext());
        categoryAdapter = new CategoryAdapter(list_category, getContext());

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);
        recyCategory.setAdapter(categoryAdapter);



        btnSearchNew.setOnClickListener(view -> {
            Fragment fragment = new SearchFragment();
            loadFragment(fragment);
        });

        btnSearchPopular.setOnClickListener(view -> {
            Fragment fragment = new SearchFragment();
            loadFragment(fragment);
        });


        return v;
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }




/*
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        NestedScrollView nestedScrollView = view.findViewById(R.id.myScrollView);
        LinearLayout layout1 = view.findViewById(R.id.layout_category_1);
        LinearLayout layout2 = view.findViewById(R.id.layout_category_2);





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



                int y = location[1]-layout2.getHeight()-93;
                Log.i("TAG",  "Y: "+y);
                Log.i("TAG", "Show + "+scrollY);
                if (y<=0){
                    layout1.setVisibility(View.VISIBLE);


                }
                else {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }
        });






        return view;


 */

    public void getBooks() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            list_book = (List<Book>) bundle.getSerializable(BOOK_LIST);

            Log.d("---------------------------HomeFrag", list_book.get(0).getTitle());
        }
    }

    public void getCategory() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            list_category = (List<Category>) bundle.getSerializable(CATEGORY_LIST);

            Log.d("---------------------------HomeFrag", list_category.get(0).getCategoryName());
        }
    }
}
