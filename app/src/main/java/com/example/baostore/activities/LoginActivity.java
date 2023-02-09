package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.baostore.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnToRegister, btnToChangePass,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnToRegister = findViewById(R.id.btnToRegister);
        btnToChangePass = findViewById(R.id.btnToChangePass);
        btnLogin = findViewById(R.id.btnLogin);

        btnToRegister.setOnClickListener(this);
        btnToChangePass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnToRegister:
                loadActivity(RegisterActivity.class);
                break;
            case R.id.btnToChangePass:
                loadActivity(ChangePassActivity.class);
                break;
            case R.id.btnLogin:
                loadActivity(MainActivity.class);
        }
    }

    private void loadActivity(Class goTo){
        Intent intent = new Intent(this, goTo);
        startActivity(intent);
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