package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Api.RetrofitCallBack.userUpdateInfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class ChangePassActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    EditText edOldPass, edNewPass, edReNewPass;
    Button btnChangePass;
    ApiService service;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        tvTitleHeader = findViewById(R.id.title);

        imgBack = findViewById(R.id.back_button);
        edOldPass = findViewById(R.id.edOldPass_cp);
        edNewPass = findViewById(R.id.edNewPass_cp);
        edReNewPass = findViewById(R.id.edReNewPass_cp);
        btnChangePass = findViewById(R.id.btnChangePass_cp);

        // header
        tvTitleHeader.setText("Thay đổi mật khẩu");

        service= GetRetrofit.getInstance().createRetrofit();
        user= SharedPrefManager.getInstance(this).getUser();

        btnChangePass.setOnClickListener(view -> {
            String oldPass = edOldPass.getText().toString().trim();
            String newPass = edNewPass.getText().toString().trim();
            String reNewPass = edReNewPass.getText().toString().trim();

            if(!checkError(oldPass,newPass,reNewPass)){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(USER_EMAIL, user.getEmail());
            jsonObject.addProperty(USER_PASSWORD, newPass);

            Call<UserResponse> call = service.updatePassword(jsonObject);
            call.enqueue(userUpdateInfo(this, 1));
            }
        });
    }

    private boolean checkError(String oldPass, String newPass, String reNewPass) {
        boolean hasError = false;
        if(oldPass.isEmpty()){
            edOldPass.setError(getResources().getString(R.string.err_pass_empty));
            hasError = true;
        }
        if (newPass.isEmpty()){
            edNewPass.setError(getResources().getString(R.string.err_pass_empty));
            hasError = true;
        }else if(newPass.length()<6){
            edNewPass.setError(getResources().getString(R.string.err_password_length));
            hasError = true;
        } else if (!newPass.equals(reNewPass)) {
            edReNewPass.setError(getResources().getString(R.string.err_not_identical));
            hasError = true;
        }
        return hasError;
    }


}
