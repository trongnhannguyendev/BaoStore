package com.example.baostore.adapters;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_OBJECT;
import static com.example.baostore.testapi.RetrofitCallBack.BookImageGetAll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.DetailItemActivity;
import com.example.baostore.models.Book;
import com.example.baostore.responses.BookImageResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private List<Book> bookList;
    ApiService service;
    Context context;

    public BookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context myContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View v = inflater.inflate(R.layout.item_layout_2, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        Utils utils = new Utils();


        double price = book.getPrice();
        holder.tvTitle.setText(book.getTitle());
        holder.tvPrice.setText(utils.priceToString(price));
        holder.tvSalePrice.setText(utils.priceToString(price));
        holder.ivBook.setScaleType(ImageView.ScaleType.FIT_XY);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = new Utils().loadImageFromURL(book.getUrl());
                holder.ivBook.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.ivBook.setImageBitmap(bitmap);
                    }
                });
            }
        });
        thread.start();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailItemActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_OBJECT, book);

                Log.d("--BookAdapter", book.getbookid()+"");

                JsonObject object = new JsonObject();
                object.addProperty(BOOK_ID, book.getbookid());
                intent.putExtras(bundle);

                service =GetRetrofit.getInstance().createRetrofit();
                Call<BookImageResponse> call = service.getImagesByBookID(object);
                call.enqueue(BookImageGetAll(context,bundle,intent));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvPrice, tvSalePrice;
        private ImageView ivBook;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.product_name);
            tvPrice = itemView.findViewById(R.id.original_price);
            tvSalePrice = itemView.findViewById(R.id.discounted_price);
            ivBook = itemView.findViewById(R.id.product_image);
        }
    }


}
