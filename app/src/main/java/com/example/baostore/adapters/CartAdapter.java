package com.example.baostore.adapters;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Api.RetrofitCallBack.cartDeleteItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.models.Book;
import com.example.baostore.models.Cart;
import com.example.baostore.models.User;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.Api.RetrofitCallBack;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<Cart> list;
    private List<Book> bookList;
    Context context;
    CartFragment fragment;
    ApiService service;

    public CartAdapter(List<Cart> list, List<Book> bookList, Context context, CartFragment fragment) {
        this.list = list;
        this.bookList = bookList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_cart, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = list.get(position);
        Book book = bookList.get(cart.getBookid()-1);

        service = GetRetrofit.getInstance().createRetrofit();
        User user = SharedPrefManager.getInstance(context).getUser();
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID, user.getUserid());
        object.addProperty(BOOK_ID, cart.getBookid());

        Call<CartResponse> addQuantity = service.increaseCartQuantity(object);
        Call<CartResponse> decreaseQuantity = service.decreaseCartQuantity(object);
        Call<CartResponse> deleteCart = service.deleteCart(object);


        int quantity = cart.getAmount();
        String price = new Utils().priceToString(cart.getPrice());
        String totalPrice = new Utils().priceToString(quantity * cart.getPrice());

        holder.tvBookPrice.setText(price);
        holder.tvTotalPrice.setText(totalPrice);
        holder.tvBookName.setText(cart.getTitle());
        holder.ivBook.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.edQuantity.setText(String.valueOf(quantity));

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

        holder.ivDecrease.setOnClickListener(view -> {



            int curQuantity = Integer.parseInt(holder.edQuantity.getText().toString());
            double oldPrice = cart.getPrice() * curQuantity;
            holder.ivIncrease.setClickable(true);
            if (curQuantity <= 1 ) {
                holder.ivDecrease.setClickable(false);
            } else {
                decreaseQuantity.clone().enqueue(RetrofitCallBack.cartNoData(context));
                curQuantity--;
                holder.edQuantity.setText(String.valueOf(curQuantity));
                double currentPrice = cart.getPrice();
                int newTotalPrice = (int) (currentPrice * curQuantity);
                holder.tvTotalPrice.setText(new Utils().priceToString(newTotalPrice));

            }


            double newPrice = cart.getPrice() * curQuantity;
            double updatePrice = newPrice - oldPrice;
            fragment.updateTotalPrice(updatePrice);
        });

        holder.ivIncrease.setOnClickListener(view -> {

            addQuantity.clone().enqueue(RetrofitCallBack.cartNoData(context));
            int curQuantity = Integer.parseInt(holder.edQuantity.getText().toString());
            if(curQuantity >= book.getQuantity()){
                holder.ivIncrease.setClickable(false);
            } else {

                double oldPrice = cart.getPrice() * curQuantity;
                holder.ivDecrease.setClickable(true);

                curQuantity++;
                holder.edQuantity.setText(String.valueOf(curQuantity));
                double currentPrice = cart.getPrice();
                int newTotalPrice = (int) (currentPrice * curQuantity);
                holder.tvTotalPrice.setText(new Utils().priceToString(newTotalPrice));

                double newPrice = cart.getPrice() * curQuantity;
                double updatePrice = newPrice - oldPrice;
                fragment.updateTotalPrice(updatePrice);
            }

        });

        holder.ivCancel.setOnClickListener(view -> {
            deleteCart.enqueue(cartDeleteItem(context, 1));
            MainActivity activity = (MainActivity) context;
            activity.loadFragment(new CartFragment());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivBook, ivCancel, ivDecrease, ivIncrease;
        public TextView tvBookName, tvBookPrice, tvTotalPrice;
        public EditText edQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName_itemcart);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice_itemcart);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice_itemcart);

            ivBook = itemView.findViewById(R.id.ivBook_itemcart);
            ivDecrease = itemView.findViewById(R.id.ivDecrease_itemcart);
            ivIncrease = itemView.findViewById(R.id.ivIncrease_itemcart);
            ivCancel = itemView.findViewById(R.id.ivCancel_itemcart);

            edQuantity = itemView.findViewById(R.id.edQuantity_itemcart);

        }
    }
}
