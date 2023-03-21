package com.example.baostore.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baostore.R;
import com.example.baostore.activities.CartInforActivity;
import com.example.baostore.activities.UserInforActivity;


public class CartFragment extends Fragment {
    private CardView cvIconProgress;
    private MotionButton btnConfirmCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // màu icon progress
        cvIconProgress=(CardView) view.findViewById(R.id.cvProgress_1);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);

        // xử lý button
        btnConfirmCart=view.findViewById(R.id.btnComnfirmCart);
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        btnConfirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CartInforActivity.class);
                startActivity(i);
            }
        });


        return view;
    }
}