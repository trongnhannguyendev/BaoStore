package com.example.baostore.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import com.example.baostore.models.Book;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.net.URL;
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

        Utils utils = new Utils();
        String price = utils.priceToString(book.getPrice());

        holder.tvTitle.setText(book.getTitle());
        holder.tvPrice.setText(price);
        holder.tvSalePrice.setText(price);
        holder.ivBook.setScaleType(ImageView.ScaleType.FIT_XY);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadImageFromURL(book.getUrl());
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


    private Bitmap loadImageFromURL(String link) {


        URL url;
        Bitmap bmp = null;
        try{
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }



}
