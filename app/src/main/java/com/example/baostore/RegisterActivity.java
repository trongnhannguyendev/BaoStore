package com.example.baostore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton btnCancel, btnReg;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnCancel = findViewById(R.id.btnCancelReg);
        btnReg = findViewById(R.id.btnReg);

        btnCancel.setOnClickListener(view ->{startActivity(new Intent(this, LoginActivity.class));});

        btnReg.setOnClickListener(view ->{startActivity(new Intent(this, LoginActivity.class));});

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelReg:
                finish();
            case R.id.btnReg:
                finish();

        }

    }

    private void createSnackbar(View v, String msg){
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