package com.example.baostore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.activities.CartInforActivity;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.UserInforActivity;
import com.example.baostore.models.User;


public class ProfileFragment extends Fragment {
    LinearLayout btnUserInfor;
    MotionButton btnLogOut;
    TextView tvFullname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvFullname = view.findViewById(R.id.tvFullname_profile);
        // ánh xạ
        btnUserInfor=view.findViewById(R.id.btnUserInfor);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        tvFullname.setText(user.getFullname());


        // xử lý thông tin cá nhân
        btnUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), UserInforActivity.class);
                startActivity(i);
            }
        });

        // Đăng xuất
        btnLogOut=view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
               getActivity().finish();
            }
        });

        return view;

    }
}