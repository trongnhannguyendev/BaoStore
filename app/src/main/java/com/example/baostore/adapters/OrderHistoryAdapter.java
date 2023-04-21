package com.example.baostore.adapters;

import static com.example.baostore.Constant.Constants.ORDER_OBJECT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.activities.DetailOrderItemActivity;
import com.example.baostore.models.Order;
import com.example.baostore.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    // TODO fix code
    public List<Order> list;
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
        //holder.tvTotalPrice.setText(order.getOrderTotal()+"");
        //holder.tvTotalQuantity.setText("3");
        holder.tvBuyDate.setText(order.getCreatedate().toString());

        Log.d(context.getString(R.string.debug_adapter_order_history), "onBindViewHolder: " + order.getState());
        if(order.getState() == 0){
            holder.tvOrderStatus.setText("Đơn hàng đang chờ xác nhận");
            holder.ivOrderStatus.setImageResource(R.drawable.ic_verify);
        }
        if(order.getState() == 1){
            holder.tvOrderStatus.setText("Đơn hàng đang được vận chuyển");
            holder.ivOrderStatus.setImageResource(R.drawable.ic_delivery);
        }
        if(order.getState() == 2){
            holder.tvOrderStatus.setText("Đơn hàng hoàn thành");
            holder.ivOrderStatus.setImageResource(R.drawable.ic_complete);
        }
        if(order.getState() == -1){
            holder.tvOrderStatus.setText("Đang chờ đã hủy");
            holder.ivOrderStatus.setImageResource(R.drawable.ic_cancel);
        }

        holder.tvToOrderHistory.setOnClickListener(view ->{
            Bundle bundle = new Bundle();
            bundle.putSerializable(ORDER_OBJECT, order);
            Intent intent = new Intent(context, DetailOrderItemActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBuyDate,tvToOrderHistory, tvTotalQuantity, tvTotalPrice, tvOrderStatus;
        ImageView ivOrderStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToOrderHistory = itemView.findViewById(R.id.tvToOrderDetail_buyhistory);
            tvBuyDate = itemView.findViewById(R.id.tvBuyDate_buyhistory);
            //tvTotalQuantity = itemView.findViewById(R.id.tvTotalQuantity_buyhistory);
            //tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice_buyhistory);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus_buyhistory);
            ivOrderStatus = itemView.findViewById(R.id.ivOrderStatus_buyhistory);
        }
    }
}
