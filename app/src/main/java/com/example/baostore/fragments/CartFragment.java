package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_PRICE;
import static com.example.baostore.Constant.Constants.BOOK_TITLE;
import static com.example.baostore.Constant.Constants.BOOK_URL;
import static com.example.baostore.Constant.Constants.CART_QUANTITY;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.activities.CartInforActivity;
import com.example.baostore.activities.UserInforActivity;
import com.example.baostore.adapters.CartAdapter;
import com.example.baostore.models.Cart;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    private CardView cvIconProgress;
    private MotionButton btnConfirmCart;
    public List<Cart> list;
    public RecyclerView recyCart;
    public CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // màu icon progress
        cvIconProgress = (CardView) view.findViewById(R.id.cvProgress_1);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);

        // xử lý button
        btnConfirmCart = view.findViewById(R.id.btnComnfirmCart);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        btnConfirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CartInforActivity.class);
                startActivity(i);
            }
        });

        list = new ArrayList<>();
        recyCart = view.findViewById(R.id.recyCart_cart);
        recyCart.setLayoutManager(new LinearLayoutManager(getContext()));

        getCart();


        return view;
    }

    public void getCart(){
        ApiService service = new GetRetrofit().getRetrofit();

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_ID, user.getUserID());

        Call<Result> call = service.getCartByUserID(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                int responseCode = response.body().getResponseCode();
                if(responseCode == RESPONSE_OKAY) {
                    JsonElement element = response.body().getData();
                    JsonArray array = element.getAsJsonArray();
                    for(JsonElement element1 : array){
                        JsonObject object = element1.getAsJsonObject();
                        int userID = object.get(USER_ID).getAsInt();
                        int bookID = object.get(BOOK_ID).getAsInt();
                        int quantity = object.get(CART_QUANTITY).getAsInt();
                        String title = object.get(BOOK_TITLE).getAsString();
                        double price = object.get(BOOK_PRICE).getAsDouble();
                        String url = object.get(BOOK_URL).getAsString();

                        Cart cart = new Cart();

                        cart.setCartId(userID);
                        cart.setBookID(bookID);
                        cart.setQuantity(quantity);
                        cart.setTitle(title);
                        cart.setPrice(price);
                        cart.setUrl(url);

                        list.add(cart);

                        Log.d("--------------------CartFrag", title);
                    }
                    adapter = new CartAdapter(list, getContext());
                    recyCart.setAdapter(adapter);


                }else{
                    Log.d("----------------CartFragment", response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("----------------------CartFrag", t.toString());
            }
        });
    }
}