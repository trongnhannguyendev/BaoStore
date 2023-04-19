package com.example.baostore.activities;

import static com.example.baostore.Api.RetrofitCallBack.userUpdateInfo;
import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_OBJECT;
import static com.example.baostore.Api.RetrofitCallBack.getVerificationCode;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.example.baostore.responses.VerificationCodeResponse;
import com.google.gson.JsonObject;

import java.io.Serializable;

import retrofit2.Call;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edPass, edRePass;
    MotionButton btnConfirm;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edPass = findViewById(R.id.ednewPass_forgotPass);
        edRePass = findViewById(R.id.edReNewPass_forgotPass);
        btnConfirm = findViewById(R.id.btnChangePass_forgotPass);
        service = GetRetrofit.getInstance().createRetrofit();

        // Change password

        btnConfirm.setOnClickListener(view -> {
            turnEditingOff();
            String email = getIntent().getStringExtra(USER_EMAIL);
            String pass = edPass.getText().toString().trim();
            String rePass = edRePass.getText().toString().trim();

            if (!checkError(pass, rePass)) {
                Intent intent =new Intent(ForgotPasswordActivity.this, CodeVerifyActivity.class);
                Bundle bundle = new Bundle();
                User user = new User();
                user.setEmail(email);
                user.setPassword(pass);
                bundle.putSerializable(USER_OBJECT, (Serializable) user);
                // Forgot password
                bundle.putInt(ACTION_CODE, 2);
                intent.putExtras(bundle);


                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL, email);
                object.addProperty(USER_PASSWORD, pass);

                Call<UserResponse> call = service.updatePassword(object);
                call.enqueue(userUpdateInfo(this,2));
            } else{
                turnEditingOn();
            }
        });
    }

    public boolean checkError(String pass, String rePass) {
        boolean hasError = false;
        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.err_pass_empty));
            hasError = true;
        } else if (!pass.equals(rePass)) {
            edRePass.setError(getResources().getString(R.string.err_not_identical));
            hasError = true;
        }
        return hasError;
    }

    public void turnEditingOff(){
        edPass.setEnabled(false);
        edRePass.setEnabled(false);
        btnConfirm.setEnabled(false);
    }

    public void turnEditingOn(){
        edPass.setEnabled(true);
        edRePass.setEnabled(true);
        btnConfirm.setEnabled(true);
    }
}