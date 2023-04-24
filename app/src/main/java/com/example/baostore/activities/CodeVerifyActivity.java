package com.example.baostore.activities;

import static com.example.baostore.Api.RetrofitCallBack.getVerificationCode;
import static com.example.baostore.Api.RetrofitCallBack.userUpdateInfo;
import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_OBJECT;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.VERIFICATION_CODE;
import static com.example.baostore.Api.RetrofitCallBack.getUserRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.User;
import com.example.baostore.models.VerificationCode;
import com.example.baostore.responses.UserResponse;
import com.example.baostore.responses.VerificationCodeResponse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.JsonObject;

import okhttp3.internal.Util;
import retrofit2.Call;

public class CodeVerifyActivity extends AppCompatActivity {
    LinearLayout llSend, llVerify;
    EditText edEmail, edVerifyCode;
    Button btnVerify, btnSend;
    MaterialToolbar toolbar;
    ApiService service;
    int actionCode;
    String email;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        llSend = findViewById(R.id.llsend);
        llVerify = findViewById(R.id.llverify);
        edEmail = findViewById(R.id.edEmail_verifycode);
        edVerifyCode = findViewById(R.id.edVerifyCode);
        btnVerify = findViewById(R.id.btnVerifyCode_verify);
        btnSend = findViewById(R.id.btnEmail_verifyCode);
        toolbar = findViewById(R.id.toolbar_vc);



        turnVerifyFieldsOff();

        intent = new Intent();
        edVerifyCode.setEnabled(false);
        btnVerify.setEnabled(false);
        actionCode = getIntent().getIntExtra(ACTION_CODE, -1);

        service = GetRetrofit.getInstance().createRetrofit();

        btnSend.setOnClickListener(view->{
            if (!checkError(edEmail.getText().toString().trim())) {
                turnEditingOff();
                email = edEmail.getText().toString().trim();
                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL, email);
                Call<VerificationCodeResponse> call = service.getEmailVerifyCode(object);
                call.enqueue(getVerificationCode(CodeVerifyActivity.this, intent));
            }
        });

        btnVerify.setOnClickListener(view-> {
            if (intent.hasExtra(VERIFICATION_CODE)) {
                int enterCode = Integer.parseInt(edVerifyCode.getText().toString().trim());
                int code = intent.getIntExtra(VERIFICATION_CODE, -1);
                Toast.makeText(this, code + "", Toast.LENGTH_SHORT).show();

                if (code == -1) {
                    Toast.makeText(this, "Can't get verification code", Toast.LENGTH_SHORT).show();
                }
                if (enterCode == code) {
                    Intent intent1 = new Intent();
                    if (actionCode == 1) {
                        intent1 = new Intent(CodeVerifyActivity.this, ForgotPasswordActivity.class);

                    }
                    if (actionCode == 2) {
                        intent1 = new Intent(CodeVerifyActivity.this, RegisterActivity.class);
                    }
                    intent1.putExtra(USER_EMAIL, email);
                    finish();
                    startActivity(intent1);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void turnEditingOff(){
        edVerifyCode.setEnabled(false);
        edEmail.setEnabled(false);
        btnSend.setEnabled(false);
        btnVerify.setEnabled(false);
    }

    public void turnEditingOn(){
        edVerifyCode.setEnabled(true);
        edEmail.setEnabled(true);
        btnSend.setEnabled(true);
        btnVerify.setEnabled(true);
    }

    public boolean checkError(String email){
        boolean hasError = false;
        Utils utils = new Utils();
        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.err_email_empty));
            hasError = true;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.err_email_format));
            hasError = true;
        }

        return hasError;
    }

    public void turnVerifyFieldsOn(){
        llSend.setVisibility(View.GONE);
        llVerify.setVisibility(View.VISIBLE);
    }

    public void turnVerifyFieldsOff(){
        llSend.setVisibility(View.VISIBLE);
        llVerify.setVisibility(View.GONE);
    }

}
