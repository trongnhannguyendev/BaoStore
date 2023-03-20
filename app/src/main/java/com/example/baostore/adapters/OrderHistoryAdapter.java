package com.example.baostore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.activities.DetailOrderItemActivity;
import com.example.baostore.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    // TODO fix code
    public List<Order> list = new ArrayList<>();
    public Context context;

    public OrderHistoryAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_buy_history, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = list.get(position);
        holder.tvTotalPrice.setText(order.getOrderTotal()+"");
        holder.tvTotalQuantity.setText("3");
        holder.tvBuyDate.setText(order.getCreateDate().toString());

        // TODO: send order detail

        holder.tvToOrderHistory.setOnClickListener(view ->{
            Intent intent = new Intent(context, DetailOrderItemActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBuyDate,tvToOrderHistory, tvTotalQuantity, tvTotalPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToOrderHistory = itemView.findViewById(R.id.tvToOrderDetail_buyhistory);
            tvBuyDate = itemView.findViewById(R.id.tvBuyDate_buyhistory);
            tvTotalQuantity = itemView.findViewById(R.id.tvTotalQuantity_buyhistory);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice_buyhistory);
        }
    }
}
