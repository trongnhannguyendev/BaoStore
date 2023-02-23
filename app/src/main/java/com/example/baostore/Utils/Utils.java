package com.example.baostore.Utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public void createSnackBar(View v, String msg){
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
