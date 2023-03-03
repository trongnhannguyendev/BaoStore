package com.example.baostore.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Cart> list;
    Context context;

    public CartAdapter(List<Cart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context myContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View v = inflater.inflate(R.layout.item_cart, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = list.get(position);

        int quantity = cart.getQuantity();
        String price = new Utils().priceToString(cart.getPrice());
        String totalPrice = new Utils().priceToString(quantity*cart.getPrice());

        holder.tvBookPrice.setText(price);
        holder.tvTotalPrice.setText(totalPrice);
        holder.tvBookName.setText(cart.getTitle());
        holder.ivBook.setScaleType(ImageView.ScaleType.FIT_XY);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = new Utils().loadImageFromURL(cart.getUrl());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivBook, ivCancel, ivDecrease, ivIncrease;
        public TextView tvBookName, tvBookPrice, tvTotalPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName_cart);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice_cart);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice_cart);

            ivBook = itemView.findViewById(R.id.ivBook_cart);
            ivDecrease = itemView.findViewById(R.id.ivDecrease_cart);
            ivIncrease = itemView.findViewById(R.id.ivIncrease_cart);
            ivCancel = itemView.findViewById(R.id.ivCancel_cart);

        }
    }
}
