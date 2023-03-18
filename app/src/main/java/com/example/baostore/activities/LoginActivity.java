package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.testapi.RetrofitCallBack.getCheckLogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister, tvForgotPass;
    MotionButton btnLogin;
    EditText edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // khởi tạo DAO

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

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(USER_EMAIL, email);
                    jsonObject.addProperty(USER_PASSWORD, password);

                    ApiService service = new GetRetrofit().getRetrofit();
                    Call<UserResponse> checkLogin = service.userLogin(jsonObject);
                    checkLogin.enqueue(getCheckLogin(LoginActivity.this));

                } else{
                    turnEditingOn();

                }

            }
        });
    }

    // Kiểm tra dl nhập vào
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

    public void turnEditingOff(){
        edEmail.setEnabled(false);
        edPassword.setEnabled(false);
        btnLogin.setEnabled(false);
        tvRegister.setEnabled(false);
    }

    public void turnEditingOn(){
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