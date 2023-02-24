package com.example.baostore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_login extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail,edPassword;
    TextView tvToChangePass,tvToReg;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail_login);
        edPassword = findViewById(R.id.edPass_login);
        btnLogin = findViewById(R.id.btn_login);
        tvToReg = findViewById(R.id.tv_register);
        tvToChangePass = findViewById(R.id.tvToChangePass);

        btnLogin.setOnClickListener(this);
        tvToReg.setOnClickListener(this);
        tvToChangePass.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, activity_register.class));
                break;
            case R.id.tvToChangePass:
                startActivity(new Intent(this, ChangePassActivity.class));
                break;
        }
    }

    private void login(){
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",email);
        jsonObject.addProperty("password",password);

        Call<Result> call = service.userLogin(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    if (!response.body().getError()) {
                        Log.d("-------------", response.body().getError()+"");
                        Log.d("-------------", response.body().getMessage()+"");
                        Log.d("-------------", response.body().getResponseCode()+"");
                        Log.d("-------------", response.body().getUser()+"");
                        if(response.body().getResponseCode() == 1) {
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else{
                            Toast.makeText(activity_login.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("-------------", response.body().getError()+"");
                        Log.d("-------------", response.body().getMessage()+"");
                    }
                } catch (Exception e){
                    Toast.makeText(activity_login.this, "Cant get response body error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage()+"");
            }
        });
    }


}
