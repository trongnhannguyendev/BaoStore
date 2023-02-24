package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister;
    MotionButton btnLogin;
    EditText edEmail,edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Chuyển màn hình Register
        tvRegister=findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        // Xử lí Đăng nhập
        btnLogin=findViewById(R.id.btnLogin);
        edEmail = findViewById(R.id.edEmail_login);
        edPassword = findViewById(R.id.edPassword_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
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
                            Toast.makeText(LoginActivity.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("-------------", response.body().getError()+"");
                        Log.d("-------------", response.body().getMessage()+"");
                    }
                } catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Cant get response body error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage()+"");
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}