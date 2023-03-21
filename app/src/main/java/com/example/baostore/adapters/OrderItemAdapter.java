package com.example.baostore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.models.Book;
import com.example.baostore.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    List<Order> list = new ArrayList<>();
    Context context;

    public OrderItemAdapter(List<Order> list, Context context) {
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
        // TODO: fix
        Order order = list.get(position);
        holder.tvTotalPrice.setText(order.getOrderTotal()+"");
        holder.tvBookName.setText("");
        holder.tvTotalQuantity.setText("");

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
