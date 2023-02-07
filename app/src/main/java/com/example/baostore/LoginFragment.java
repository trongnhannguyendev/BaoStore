package com.example.baostore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements View.OnClickListener {
    MaterialButton btnToReg, btnToChangePass,btnLogin;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_login, container, false);
        btnToReg = v.findViewById(R.id.btnToRegister);
        btnToChangePass = v.findViewById(R.id.btnToChangePass);
        btnLogin = v.findViewById(R.id.btnLogin);

        btnToReg.setOnClickListener(this);
        btnToChangePass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                startActivity(new Intent(getContext(), MainActivity.class));
                snackbar(getView(), "Login successfully");
                break;
            case R.id.btnToChangePass:
                fragment = new ChangePassFragment();
                loadFragment(fragment);
                break;
            case R.id.btnToRegister:
                fragment = new RegisterFragment();
                loadFragment(fragment);
                break;
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentManager manager = getParentFragmentManager();
        manager.beginTransaction().replace(R.id.flLogin, fragment).addToBackStack(null).commit();
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