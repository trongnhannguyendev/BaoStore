package com.example.baostore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.baostore.R;
import com.example.baostore.activities.UserInforActivity;


public class ProfileFragment extends Fragment {
    LinearLayout btnUserInfor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // ánh xạ
        btnUserInfor=view.findViewById(R.id.btnUserInfor);

        // xử lý thông tin cá nhân
        btnUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UserInforActivity.class);
                startActivity(i);
            }
        });

        return view;

    }
}