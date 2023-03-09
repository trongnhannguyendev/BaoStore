package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
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
import android.widget.TextView;

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
import com.example.baostore.DAOs.CartDAO;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.CartInforActivity;
import com.example.baostore.activities.UserInforActivity;
import com.example.baostore.adapters.Book2Adapter;
import com.example.baostore.adapters.BookAdapter;
import com.example.baostore.adapters.CartAdapter;
import com.example.baostore.models.Address;
import com.example.baostore.models.Book;
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
    public TextView tvTotalPrice;
    public CartDAO cartDAO;
    public double totalCartPrice;
    public Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tvTotalPrice = view.findViewById(R.id.tvTotalPrice_cart);
        cartDAO = new CartDAO();

        // màu icon progress
        cvIconProgress = (CardView) view.findViewById(R.id.cvProgress_1);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);

        // xử lý button
        btnConfirmCart = view.findViewById(R.id.btnComnfirmCart);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bundle = getArguments();
        btnConfirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CartInforActivity.class);
                if(bundle!= null && bundle.containsKey(ADDRESS_LIST)){
                    i.putExtras(bundle);
                }
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
                Log.d(getResources().getString(R.string.debug_CartFragment), responseCode+"");
                if(responseCode == RESPONSE_OKAY) {
                    JsonElement element = response.body().getData();
                    JsonArray array = element.getAsJsonArray();
                    list = cartDAO.getData(array);

                    CartFragment fragment = (CartFragment) getParentFragment();
                    adapter = new CartAdapter(list, getContext(), CartFragment.this);

                    recyCart.setAdapter(adapter);
                    totalCartPrice = 0;
                    for (int i = 0; i < list.size(); i++) {
                        totalCartPrice += list.get(i).getPrice() * list.get(i).getQuantity();

                    }
                    tvTotalPrice.setText(new Utils().priceToString(totalCartPrice));

                }else{
                    Log.d(getResources().getString(R.string.debug_CartFragment), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(getResources().getString(R.string.debug_CartFragment), t.toString());
            }
        });

    }

    public void updateTotalPrice(double updatePrice){
        totalCartPrice += updatePrice;
        tvTotalPrice.setText(new Utils().priceToString(totalCartPrice));

    }


}