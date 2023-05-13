package com.example.baostore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.fragments.SearchFragment;
import com.example.baostore.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
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
        View v = inflater.inflate(R.layout.item_category, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = list.get(position);
        holder.tvName_category.setText(category.getCategoryname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                SearchFragment fragment = new SearchFragment();
                mainActivity.setSearchSelection();
                mainActivity.loadSearchFragment(fragment,2,category.getCategoryname()+"");

                Log.d(context.getString(R.string.debug_CartAdapter), "category name: "+category.getCategoryname()+"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName_category = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
