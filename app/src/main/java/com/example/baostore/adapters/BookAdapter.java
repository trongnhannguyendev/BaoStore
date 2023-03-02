package com.example.baostore.adapters;

import static com.example.baostore.Constant.Constants.*;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.DetailItemActivity;
import com.example.baostore.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> list;
    Context context;

    public BookAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context myContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View v = inflater.inflate(R.layout.item_layout_2, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);

        String price = new Utils().priceToString(book.getPrice());

        holder.tvTitle.setText(book.getTitle());
        holder.tvPrice.setText(price);
        holder.tvSalePrice.setText(price);
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
                Toast.makeText(context, book.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), DetailItemActivity.class);


                Bundle bundle = new Bundle();
                bundle.putInt(BOOK_ID, book.getBookID());
                bundle.putString(BOOK_TITLE, book.getTitle());
                bundle.putDouble(BOOK_PRICE, book.getPrice());
                bundle.putInt(BOOK_QUANTITY, book.getQuantity());
                bundle.putInt(BOOK_CATEGORY_ID, book.getCategoryID());
                bundle.putInt(BOOK_AUTHOR_ID, book.getAuthorID());
                bundle.putInt(BOOK_PUBLISHER_ID, book.getPublisherID());
                bundle.putString(BOOK_URL, book.getUrl());

                intent.putExtras(bundle);


                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
