package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Api.RetrofitCallBack.getCheckLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.MyLocale;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegister, tvForgotPass;
    MotionButton btnLogin;
    Spinner spnLanguage;
    EditText edEmail, edPassword;
    ApiService service;
    boolean canExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


            // ẩn thanh pin
            if (Build.VERSION.SDK_INT >= 16) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }

            // Chuyển màn hình Register
            tvRegister = findViewById(R.id.tvRegister);
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(LoginActivity.this, CodeVerifyActivity.class);
                    i.putExtra(ACTION_CODE, 2);
                    startActivity(i);
                }
            });

            // Chuyển màn hình quên mật khẩu
            tvForgotPass = findViewById(R.id.tvForgotPass);
            tvForgotPass.setOnClickListener(view -> {
                Intent i = new Intent(LoginActivity.this, CodeVerifyActivity.class);
                i.putExtra(ACTION_CODE, 1);
                startActivity(i);
            });

            // Xử lí Đăng nhập
            btnLogin = findViewById(R.id.btnLogin);
            edEmail = findViewById(R.id.edEmail_login);
            edPassword = findViewById(R.id.edPassword_login);
            service = GetRetrofit.getInstance().createRetrofit();


            btnLogin.setOnClickListener(view -> {
                // Tắt chỉnh sửa khi đang send query
                turnEditingOff();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (!checkError(email, password)) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(USER_EMAIL, email);
                    jsonObject.addProperty(USER_PASSWORD, password);

                    Call<UserResponse> checkLogin = service.userLogin(jsonObject);
                    checkLogin.enqueue(getCheckLogin(LoginActivity.this));

                } else {
                    turnEditingOn();
                }

            /* Compare using bcrypt
            String bcryptHashString = "$2a$12$f3S3LFnHfEuhs8DezwNU5.3gZbpm9c0Mn8Aw9qWU.N2R1kDNlod6O";
            String pass = edtSIPassword.getText().toString();
            BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), bcryptHashString);
            if (result.verified) {
                Toast.makeText(this, "match", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "notmatch", Toast.LENGTH_LONG).show();
            }*/

            });
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
        }
    }

    // Kiểm tra dữ liệu nhập vào
    private boolean checkError(String email, String password){
        boolean hasError = false;
        if(email.isEmpty()){
            edEmail.setError(getResources().getString(R.string.err_email_empty));
            hasError = true;
        }
        if (password.isEmpty()){
            edPassword.setError(getResources().getString(R.string.err_pass_empty));
            hasError = true;
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