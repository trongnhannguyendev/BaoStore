package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_OBJECT;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.testapi.RetrofitCallBack.getUserRegister;
import static com.example.baostore.testapi.RetrofitCallBack.getVerificationCode;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.example.baostore.responses.VerificationCodeResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import java.io.Serializable;

import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {
    EditText edEmail, edPass, edRePass, edPhoneNumber, edFullName;
    MotionButton btnCancel, btnRegister;
    Utils utils;
    ActionCodeSettings actionCodeSettings;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edEmail = findViewById(R.id.edEmail_reg);
        edPass = findViewById(R.id.edPassword_reg);
        edRePass = findViewById(R.id.edRePassword_reg);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_reg);
        edFullName = findViewById(R.id.edFullName_reg);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);

        service = GetRetrofit.getInstance(this).getRetrofit();

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Hủy
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Ánh xạ




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
            if (!checkError(email, pass, pass_re, phoneNumber, fullname)) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(pass);
                user.setPhonenumber(phoneNumber);
                user.setFullname(fullname);

                // Lưu user vào bundle qua verify code
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_OBJECT, (Serializable) user);
                bundle.putInt(ACTION_CODE, 1);
                Intent intent = new Intent(RegisterActivity.this, CodeVerifyActivity.class);
                intent.putExtras(bundle);
                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL, email);
                Call<VerificationCodeResponse> call = service.getEmailVerifyCode(object);
                call.enqueue(getVerificationCode(RegisterActivity.this, intent));


            } else{
                turnEditingOn();
            }

        });

    }

    private boolean checkError(String email, String pass, String pass_re, String phoneNumber, String fullname) {
        utils = new Utils();

        boolean hasError = false;

        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.err_pass_empty));
            hasError = true;
        } else if (pass.length() <= 6) {
            edPass.setError(getResources().getString(R.string.err_password_length));
            hasError = true;
        }
        if (!pass.equals(pass_re)) {
            edRePass.setError(getResources().getString(R.string.err_not_identical));
            hasError = true;
        }
        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_empty));
            hasError = true;
        } else if (phoneNumber.length() != 10) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_format));
            hasError = true;
        } else if (!utils.isNumeric(phoneNumber)) {
            edPhoneNumber.setError(getResources().getString(R.string.err_num_format));
            hasError = true;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.err_email_empty));
            hasError = true;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.err_num_format));
            hasError = true;
        }

        if (fullname.isEmpty()) {
            edFullName.setError(getResources().getString(R.string.err_fullname_empty));
            hasError = true;
        }
        return hasError;
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



