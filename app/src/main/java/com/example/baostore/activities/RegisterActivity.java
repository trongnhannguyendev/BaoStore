package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.*;
import static com.example.baostore.testapi.RetrofitCallBack.userRegister;

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
    EditText edEmail, edPass, edRePass, edPhoneNumber, edFullName;
    MotionButton btnCancel, btnRegister;
    UserDAO userDAO;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDAO = new UserDAO(this);

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

        // Ánh xạ
        btnRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.edEmail_reg);
        edPass = findViewById(R.id.edPassword_reg);
        edRePass = findViewById(R.id.edRePassword_reg);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_reg);
        edFullName = findViewById(R.id.edFullName_reg);


        // Đăng ký
        btnRegister.setOnClickListener(view -> {
            turnEditingOff();
            // Lấy dữ liệu
            String email = edEmail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            String pass_re = edRePass.getText().toString().trim();
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            String fullname = edFullName.getText().toString().trim();

            // Chạy đăng ký nếu không có lỗi
            if (checkError(email, pass, pass_re, phoneNumber, fullname)) {
                //register(email, pass, fullname, phoneNumber);
                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL,email);
                object.addProperty(USER_PASSWORD,pass);
                object.addProperty(USER_PHONE_NUMBER,phoneNumber);
                object.addProperty(USER_FULL_NAME, fullname);

                userRegister(RegisterActivity.this, object);
            } else{
                turnEditingOn();
            }

        });

    }
/*

    public void register(String email, String pass, String fullname, String phoneNumber) {
        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject emailObject = new JsonObject();
        emailObject.addProperty(USER_EMAIL, email);

        Call<Result> checkEmailCall = service.checkUserEmailExist(emailObject);
        checkEmailCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                boolean callError = response.body().getError();
                int responseCode = response.body().getResponseCode();
                String callMsg = response.body().getMessage();
                String log = new Utils().logEnqueueMsg(callError, responseCode, callMsg);
                Log.d(getResources().getString(R.string.debug_RegisterActivity), log);

                // Hủy nếu email tồn tại
                if (responseCode == 1) {
                    Toast.makeText(RegisterActivity.this, "Email đã sử dụng!", Toast.LENGTH_SHORT).show();
                    turnEditingOn();
                    return;
                }

                // Lưu người dùng nếu email không tồn tại
                JsonObject userObject = new JsonObject();
                userObject.addProperty(USER_EMAIL, email);
                userObject.addProperty(USER_PASSWORD, pass);
                userObject.addProperty(USER_FULL_NAME, fullname);
                userObject.addProperty(USER_PHONE_NUMBER, phoneNumber);

                // Gọi đăng ký
                Call<Result> regCall = service.registerUser(userObject);

                regCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        boolean callError = response.body().getError();
                        int responseCode = response.body().getResponseCode();
                        String callMsg = response.body().getMessage();
                        String log = new Utils().logEnqueueMsg(callError, responseCode, callMsg);
                        Log.d(getResources().getString(R.string.debug_RegisterActivity), log);


                        if (responseCode == RESPONSE_OKAY) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            turnEditingOn();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        turnEditingOn();
                        Log.d(getResources().getString(R.string.debug_RegisterActivity), String.valueOf(t.getMessage()));
                        Toast.makeText(RegisterActivity.this, "Something wrong happen", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                turnEditingOn();
                Log.d(getResources().getString(R.string.debug_RegisterActivity), String.valueOf(t.getMessage()));

            }
        });
    }
*/

    private boolean checkError(String email, String pass, String pass_re, String phoneNumber, String fullname) {
        utils = new Utils();

        boolean noError = true;

        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.no_pass));
            noError = false;
        } else if (pass.length() <= 6) {
            edPass.setError(getResources().getString(R.string.password_length));
            noError = false;
        }
        if (!pass.equals(pass_re)) {
            edRePass.setError(getResources().getString(R.string.pass_not_equal_repass));
            noError = false;
        }
        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.no_phonenumber));
            noError = false;
        } else if (phoneNumber.length() != 10) {
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
            edFullName.setError(getResources().getString(R.string.no_fullname));
            noError = false;
        }
        return noError;
    }

    public void turnEditingOff(){
        edEmail.setEnabled(false);
        edPass.setEnabled(false);
        edRePass.setEnabled(false);
        edPhoneNumber.setEnabled(false);
        edFullName.setEnabled(false);
        btnRegister.setEnabled(false);
        btnCancel.setEnabled(false);

    }

    public void turnEditingOn(){
        edEmail.setEnabled(true);
        edPass.setEnabled(true);
        edRePass.setEnabled(true);
        edPhoneNumber.setEnabled(true);
        edFullName.setEnabled(true);
        btnRegister.setEnabled(true);
        btnCancel.setEnabled(true);
    }
}



