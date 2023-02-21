//package com.example.baostore.fragments;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.baostore.R;
//
//
//public class CartInforFragment extends Fragment {
//
//    TextView tvTitleHeader;
//    ImageView imgBack;
//    private Toolbar toolbar;
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_cart_infor, container, false);
//
//        //header
//        tvTitleHeader = view.findViewById(R.id.title);
//        tvTitleHeader.setText("Thông tin nhận hàng");
//
//        //button back
//        imgBack = view.findViewById(R.id.back_button);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//            return view;
//    }
//}