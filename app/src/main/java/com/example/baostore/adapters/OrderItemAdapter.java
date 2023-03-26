package com.example.baostore.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Book;
import com.example.baostore.models.Order;
import com.example.baostore.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    List<OrderDetail> list;
    Context context;

    public OrderItemAdapter(List<OrderDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_layout_order, null, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Utils utils = new Utils();
        OrderDetail orderDetail = list.get(position);
        holder.tvTotalPrice.setText(utils.priceToString(orderDetail.getPrice()));
        holder.tvBookName.setText(orderDetail.getTitle());
        holder.tvTotalQuantity.setText("x"+orderDetail.getAmount());
        holder.ivBook.setScaleType(ImageView.ScaleType.FIT_XY);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = new Utils().loadImageFromURL(orderDetail.getUrl());
                holder.ivBook.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.ivBook.setImageBitmap(bitmap);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivBook;
        public TextView tvBookName, tvTotalPrice, tvTotalQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBook = itemView.findViewById(R.id.product_image_order);
            tvBookName = itemView.findViewById(R.id.product_name_order);
            tvTotalPrice = itemView.findViewById(R.id.original_price_order);
            tvTotalQuantity = itemView.findViewById(R.id.discounted_price_order);

        }
    }
}
