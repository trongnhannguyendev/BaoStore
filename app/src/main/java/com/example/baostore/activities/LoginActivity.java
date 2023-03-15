package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.baostore.Utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister, tvForgotPass;
    MotionButton btnLogin;
    EditText edEmail, edPassword;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // khởi tạo DAO
        userDAO = new UserDAO(this);

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }


        // Chuyển màn hình Register
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvForgotPass.setOnClickListener(view ->{
            Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(i);
        });

        // Xử lí Đăng nhập
        btnLogin = findViewById(R.id.btnLogin);
        edEmail = findViewById(R.id.edEmail_login);
        edPassword = findViewById(R.id.edPassword_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnEditingOff();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if(checkError(email, password)) {
                    login(email, password);
                } else{
                    turnEditingOn();
                }

            }
        });
    }

    // Đăng nhập
    public void login(String email, String password) {
        ApiService service = new GetRetrofit().getRetrofit();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_EMAIL, email);
        jsonObject.addProperty(USER_PASSWORD, password);

        Call<Result> call = service.userLogin(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // Lấy dữ liệu từ response
                boolean callError = response.body().getError();
                int responseCode = response.body().getResponseCode();
                String callMsg = response.body().getMessage();
                String showLog = new Utils().logEnqueueMsg(callError,responseCode,callMsg);

                Log.d(getResources().getString(R.string.debug_LoginActivity), showLog);

                if (responseCode == RESPONSE_OKAY) {
                    // Chuyển dữ liệu thành JsonObject
                    JsonElement element = response.body().getData();
                    JsonArray array = element.getAsJsonArray();
                    JsonObject object = array.get(0).getAsJsonObject();

                    // Lưu dữ liệu vào sharedPreference
                    userDAO.saveLoginInfo(object);

                    // chuyển qua MainActivity
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    // thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
                turnEditingOn();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                turnEditingOn();
                Log.d("-------------", t.getMessage() + "");
                Toast.makeText(LoginActivity.this, "Hãy thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkError(String email, String password){
        boolean hasError = true;
        if(email.isEmpty()){
            edEmail.setError(getResources().getString(R.string.no_email));
            hasError = false;
        }
        if (password.isEmpty()){
            edPassword.setError(getResources().getString(R.string.no_pass));
            hasError = false;
        }
        return hasError;
    }

    private void turnEditingOff(){
        edEmail.setEnabled(false);
        edPassword.setEnabled(false);
        btnLogin.setEnabled(false);
        tvRegister.setEnabled(false);
    }

    private void turnEditingOn(){
        edEmail.setEnabled(true);
        edPassword.setEnabled(true);
        btnLogin.setEnabled(true);
        tvRegister.setEnabled(true);
    }

    // Nhấn quay lại 2 lần để thoát app
    boolean canExit = false;

    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, R.string.msg_exit_app, Toast.LENGTH_SHORT).show();
            canExit = !canExit;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    canExit = false;
                }
            }, 1000);
        }
    }

}