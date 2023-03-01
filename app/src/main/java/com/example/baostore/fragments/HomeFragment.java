package com.example.baostore.fragments;




import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.DAOs.TempBookDAO;
import com.example.baostore.DAOs.TempCategoryDAO;
import com.example.baostore.R;

import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.adapters.BookAdapter;
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
    TempBookDAO tempBookDAO;
    TempCategoryDAO tempCategoryDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home, container, false);

        list_book = new ArrayList<>();
        list_category= new ArrayList<>();

        recyBook_Popular = v.findViewById(R.id.recyBook_Popular);
        recyBook_New = v.findViewById(R.id.recyBook_new);
        recyCategory = v.findViewById(R.id.recyCategory);

        tempBookDAO = new TempBookDAO(getContext(),this,recyBook_Popular,recyBook_New);
        tempCategoryDAO = new TempCategoryDAO(getContext(), recyCategory);

        recyBook_Popular.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyBook_New.setLayoutManager(new LinearLayoutManager(getContext()));
        recyCategory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        tempBookDAO.getBooks();
        tempCategoryDAO.getCategory();



        return v;
    }

    public void showBooks(){
        //list_book = bookDAO.getBooks();
        adapter = new BookAdapter(list_book,getContext());
        book2Adapter = new Book2Adapter(list_book,getContext());

        recyBook_Popular.setAdapter(adapter);
        recyBook_New.setAdapter(book2Adapter);

        adapter.notifyDataSetChanged();
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
    }

    /*
    private void getBooks() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Result> call = service.getbook();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                JsonArray myArr = response.body().getData();

                Log.d("------------------------", myArr.size()+"");
                for(JsonElement jsonElement: myArr){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    int bookID = jsonObject.get("bookid").getAsInt();
                    String title = jsonObject.get("title").getAsString();
                    double price = jsonObject.get("price").getAsDouble();
                    String url = jsonObject.get("url").getAsString();

                    Book book = new Book(bookID,title, price,url);
                    Log.d("--------------------",book.getTitle());
                    list_book.add(book);

                }


                adapter = new BookAdapter(list_book,getContext());
                book2Adapter = new Book2Adapter(list_book,getContext());

                recyBook_Popular.setAdapter(adapter);
                recyBook_New.setAdapter(book2Adapter);

                adapter.notifyDataSetChanged();
                book2Adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
        }
     */
}