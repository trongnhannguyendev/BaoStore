package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.*;

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
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.DAOs.UserDAO;
import com.example.baostore.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister;
    MotionButton btnLogin;
    EditText edEmail,edPassword;
    UserDAO userDAO;
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
        userDAO = new UserDAO(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                login(email, password);

            }
        });
    }

    public void login(String email, String password){
        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_EMAIL,email);
        jsonObject.addProperty(USER_PASSWORD,password);

        Call<Result> call = service.userLogin(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                    if (!response.body().getError()) {
                        Log.d("-------------", response.body().getError() + "");
                        Log.d("-------------", response.body().getMessage() + "");
                        Log.d("-------------", response.body().getResponseCode() + "");
                        if (response.body().getResponseCode() == 1) {
                            JsonElement element = response.body().getData();
                            JsonArray array = element.getAsJsonArray();
                            JsonObject object = array.get(0).getAsJsonObject();
                            userDAO.saveLoginInfo(object);

                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("-------------", response.body().getError() + "");
                        Log.d("-------------", response.body().getMessage() + "");
                    }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage() + "");
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Nhấn back 2 lần để thoát app
    boolean canExit = false;
    @Override
    public void onBackPressed() {
        if(canExit) {
            super.onBackPressed();
        } else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            canExit = !canExit;
        }
    }

}