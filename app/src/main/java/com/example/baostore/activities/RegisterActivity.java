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
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.Result;
import com.example.baostore.DAOs.UserDAO;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edEmail, edPass, edRePass, edPhoneNumber, edFullname;
    MotionButton btnCancel, btnRegister;
    UserDAO dao;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dao = new UserDAO(this);

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Hủy
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Đăng ký
        btnRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.edEmail_reg);
        edPass = findViewById(R.id.edPassword_reg);
        edRePass = findViewById(R.id.edRePassword_reg);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_reg);
        edFullname = findViewById(R.id.edFullname_reg);

        btnRegister.setOnClickListener(view -> {
            String email = edEmail.getText().toString();
            String pass = edPass.getText().toString();
            String pass_re = edRePass.getText().toString();
            String phoneNumber = edPhoneNumber.getText().toString();
            String fullname = edFullname.getText().toString();

            if (checkError(email, pass, pass_re, phoneNumber, fullname)) {
                register(email, pass, fullname, phoneNumber);
            } else {
                Toast.makeText(this, "Còn lỗi trong form", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void register(String email, String pass, String fullname, String phoneNumber) {
        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_EMAIL, email);

        Call<Result> call = service.checkUserEmailExist(jsonObject);


        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.body().getError()) {
                    if (response.body().getResponseCode() == 1) {
                        Toast.makeText(RegisterActivity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty(USER_EMAIL, email);
                    jsonObject1.addProperty(USER_PASSWORD, pass);
                    jsonObject1.addProperty(USER_FULL_NAME, fullname);
                    jsonObject1.addProperty(USER_PHONE_NUMBER, phoneNumber);

                    Log.d("----------------------------", jsonObject1 + "");

                    Call<Result> regCall = service.registerUser(jsonObject1);

                    regCall.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Log.d("-------------", response.body().getError() + "");
                            Log.d("-------------", response.body().getMessage() + "");
                            Log.d("-------------", response.body().getResponseCode() + "");

                            int responseCode = response.body().getResponseCode();
                            if (responseCode == RESPONSE_OKAY) {

                                Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();

                                finish();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            } else {
                                Toast.makeText(RegisterActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.d("-------------", t.getMessage() + "");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage() + "");

            }
        });
    }

    private boolean checkError(String email, String pass, String pass_re, String phoneNumber, String fullname) {
        utils = new Utils();

        boolean noError = true;


        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.no_pass));
            noError = false;
        }
        if (!pass.equals(pass_re)) {
            edRePass.setError(getResources().getString(R.string.pass_not_equal_repass));
            noError = false;
        }
        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.no_phonenumber));
            noError = false;
        } else if(phoneNumber.length() != 10){
            edPhoneNumber.setError(getResources().getString(R.string.not_phone_number));
            noError = false;
        } else if (!utils.isNumeric(phoneNumber)) {
            edPhoneNumber.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.no_email));
            noError = false;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if (fullname.isEmpty()) {
            edFullname.setError(getResources().getString(R.string.no_fullname));
            noError = false;
        }
        if (noError) {
            return true;
        }
        return false;
    }
}






