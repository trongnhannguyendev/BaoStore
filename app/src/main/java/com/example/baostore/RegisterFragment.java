package com.example.baostore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    MaterialButton btnReg, btnRegCancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        btnReg = v.findViewById(R.id.btnReg);
        btnRegCancel = v.findViewById(R.id.btnCancelReg);

        btnReg.setOnClickListener(this);
        btnRegCancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnReg:
                getActivity().getSupportFragmentManager().popBackStack();
                snackbar(getView(), "Register successful");
                break;
            case R.id.btnCancelReg:
                getActivity().getSupportFragmentManager().popBackStack();
                snackbar(getView(), "Registration cancelled");
                break;
        }
    }

    private void snackbar(View v, String text){
        Snackbar mySnackbar = Snackbar.make(v,text, Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySnackbar.dismiss();
            }
        });
        mySnackbar.show();
    }


}