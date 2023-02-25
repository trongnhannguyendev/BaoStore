package com.example.baostore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{
    private List<Category> list;
    Context context;

    public CategoryAdapter(List<Category> list, Context context) {
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
        Category category = list.get(position);
        holder.tvName_category.setText(category.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, category.getCategoryName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName_category;
        private ImageView ivCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName_category = itemView.findViewById(R.id.tvCategoryName);
            ivCategory = itemView.findViewById(R.id.ivCategory);
        }
    }
}
