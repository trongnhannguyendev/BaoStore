package com.example.baostore.adapters;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.CART_QUANTITY;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_ID;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.models.Cart;
import com.example.baostore.models.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<Cart> list;
    Context context;
    CartFragment fragment;

    public CartAdapter(List<Cart> list, Context context, CartFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
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
            if (curQuantity <= 1) {
                holder.ivDecrease.setClickable(false);
            } else {
                curQuantity--;
                holder.edQuantity.setText(String.valueOf(curQuantity));
                double currentPrice = cart.getPrice();
                int newTotalPrice = (int) (currentPrice * curQuantity);
                holder.tvTotalPrice.setText(new Utils().priceToString(newTotalPrice));

                decreaseCartQuantity(cart);
            }


            double newPrice = cart.getPrice() * curQuantity;
            double updatePrice = newPrice - oldPrice;
            fragment.updateTotalPrice(updatePrice);
        });

        holder.ivIncrease.setOnClickListener(view -> {
            int curQuantity = Integer.parseInt(holder.edQuantity.getText().toString());
            double oldPrice = cart.getPrice() * curQuantity;
            holder.ivDecrease.setClickable(true);

            curQuantity++;
            holder.edQuantity.setText(String.valueOf(curQuantity));
            double currentPrice = cart.getPrice();
            int newTotalPrice = (int) (currentPrice * curQuantity);
            holder.tvTotalPrice.setText(new Utils().priceToString(newTotalPrice));

            increaseCartQuantity(cart);
            double newPrice = cart.getPrice() * curQuantity;
            double updatePrice = newPrice - oldPrice;
            fragment.updateTotalPrice(updatePrice);
        });

        holder.ivCancel.setOnClickListener(view -> {


            JsonObject object = new JsonObject();
            User user = SharedPrefManager.getInstance(context).getUser();
            int id = user.getUserid();

            object.addProperty(USER_ID, id);
            object.addProperty(BOOK_ID, cart.getBookID());

            Log.d(context.getResources().getString(R.string.debug_CartAdapter), id + "");
            Log.d(context.getResources().getString(R.string.debug_CartAdapter), cart.getBookID() + "");

            ApiService service = new GetRetrofit().getRetrofit();
            Call<Result> call = service.deleteCart(object);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    int responseCode = response.body().getResponseCode();
                    if (responseCode == RESPONSE_OKAY) {
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                        MainActivity activity = (MainActivity) context;
                        Fragment fragment1 = new CartFragment();
                        activity.loadFragment(fragment1);
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                    Log.d(context.getResources().getString(R.string.debug_CartAdapter), t.toString());

                }
            });
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
            tvBookName = itemView.findViewById(R.id.tvBookName_cart);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice_cart);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice_cart);

            ivBook = itemView.findViewById(R.id.ivBook_cart);
            ivDecrease = itemView.findViewById(R.id.ivDecrease_cart);
            ivIncrease = itemView.findViewById(R.id.ivIncrease_cart);
            ivCancel = itemView.findViewById(R.id.ivCancel_cart);

            edQuantity = itemView.findViewById(R.id.edQuantity);

        }
    }

    private void increaseCartQuantity(Cart cart) {
        User user = SharedPrefManager.getInstance(context).getUser();

        ApiService service = new GetRetrofit().getRetrofit();
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID, user.getUserid());
        object.addProperty(BOOK_ID, cart.getBookID());
        Call<Result> call = service.increaseCartQuantity(object);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode != RESPONSE_OKAY) {
                    Toast.makeText(context, "Fail to update quantity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("----Cartadapter", t.toString());
            }
        });
    }

    private void decreaseCartQuantity(Cart cart) {
        User user = SharedPrefManager.getInstance(context).getUser();

        ApiService service = new GetRetrofit().getRetrofit();
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID, user.getUserid());
        object.addProperty(BOOK_ID, cart.getBookID());
        Call<Result> call = service.decreaseCartQuantity(object);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode != RESPONSE_OKAY) {
                    Toast.makeText(context, "Fail to update quantity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("----Cartadapter", t.toString());
            }
        });
    }
}
