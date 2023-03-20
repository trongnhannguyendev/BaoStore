package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.CART_LIST;
import static com.example.baostore.Constant.Constants.CART_TOTAL_PRICE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.CartInforActivity;
import com.example.baostore.adapters.CartAdapter;
import com.example.baostore.models.Cart;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    private CardView cvIconProgress;
    private MotionButton btnConfirmCart;
    public List<Cart> list;
    public RecyclerView recyCart;
    public CartAdapter adapter;
    public TextView tvTotalPrice;
    public double totalCartPrice;
    public Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tvTotalPrice = view.findViewById(R.id.tvTotalPrice_cart);

        // màu icon progress
        cvIconProgress = (CardView) view.findViewById(R.id.cvProgress_1);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);

        // xử lý button
        btnConfirmCart = view.findViewById(R.id.btnComnfirmCart);
        bundle = getArguments();


        btnConfirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CartInforActivity.class);

                bundle.putDouble(CART_TOTAL_PRICE, totalCartPrice);

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

    public void updateTotalPrice(double updatePrice){
        totalCartPrice += updatePrice;
        tvTotalPrice.setText(new Utils().priceToString(totalCartPrice));

    }

    public void getCart() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CART_LIST)) {
            list = (List<Cart>) bundle.getSerializable(CART_LIST);
            for (int i = 0; i < list.size(); i++) {
                totalCartPrice += list.get(i).getPrice() * list.get(i).getQuantity();
            }
            tvTotalPrice.setText(new Utils().priceToString(totalCartPrice));

            adapter = new CartAdapter(list, getContext(), this);

            recyCart.setAdapter(adapter);

            Log.d("---------------------------CartFrag", list.get(0).getTitle());
        } else {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCart();
                }
            }, 1000);
        }
    }


}