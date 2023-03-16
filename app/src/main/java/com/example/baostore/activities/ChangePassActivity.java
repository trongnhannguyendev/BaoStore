package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    EditText edOldPass, edNewPass, edReNewPass;
    Button btnChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        // header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Thay đổi mật khẩu");

        edOldPass = findViewById(R.id.edOldPass_cp);
        edNewPass = findViewById(R.id.edNewPass_cp);
        edReNewPass = findViewById(R.id.edReNewPass_cp);
        btnChangePass = findViewById(R.id.btnChangePass_cp);

        btnChangePass.setOnClickListener(view -> {

            changePass();


        });

    }


    private void changePass() {
        boolean hasError = false;

        String oldPass = edOldPass.getText().toString().trim();
        String newPass = edNewPass.getText().toString().trim();
        String reNewPass = edReNewPass.getText().toString().trim();

        if (!newPass.equals(reNewPass)) {
            Toast.makeText(this, getResources().getString(R.string.pass_not_equal_repass), Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldPass.isEmpty() || newPass.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_pass) + "", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        User user = SharedPrefManager.getInstance(this).getUser();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_EMAIL, user.getEmail());
        jsonObject.addProperty(USER_PASSWORD, oldPass);

/*
        Call<Result> loginCall = service.userLogin(jsonObject);

        loginCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getResponseCode() == RESPONSE_OKAY) {

                    JsonObject updateObj = new JsonObject();
                    updateObj.addProperty(USER_EMAIL, user.getEmail());
                    updateObj.addProperty(USER_PASSWORD, newPass);

                    Call<Result> updateCall = service.updatePassword(updateObj);
                    updateCall.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            int responseCode = response.body().getResponseCode();
                            Log.d("-------------------", response.body().getMessage());
                            if (responseCode == 1) {
                                Toast.makeText(ChangePassActivity.this, "update successful", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(ChangePassActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });



 */
    }


}
