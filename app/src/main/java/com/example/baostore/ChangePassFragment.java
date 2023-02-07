package com.example.baostore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;


public class ChangePassFragment extends Fragment {
    MaterialButton btnSave;
    MaterialToolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_change_pass, container, false);
        btnSave = v.findViewById(R.id.btnChangePass);
        toolbar = v.findViewById(R.id.myToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar(view, "Cancel");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnSave.setOnClickListener(view ->{
            snackbar(view,"Change password successfully");
            getActivity().getSupportFragmentManager().popBackStack();
        });
        return v;
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