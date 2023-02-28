package com.example.baostore.activities;

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
        tvTitleHeader=findViewById(R.id.title);
        tvTitleHeader.setText("Thay đổi mật khẩu");

        edOldPass = findViewById(R.id.edOldPass_cp);
        edNewPass = findViewById(R.id.edNewPass_cp);
        edReNewPass = findViewById(R.id.edReNewPass_cp);
        btnChangePass = findViewById(R.id.btnChangePass_cp);

        btnChangePass.setOnClickListener(view ->{

        changePass();


        });

    }

    private void changePass(){
        boolean hasError = false;

        String oldPass = edOldPass.getText().toString();
        String newPass = edNewPass.getText().toString();
        String reNewPass = edReNewPass.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        User user = SharedPrefManager.getInstance(this).getUser();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email",user.getEmail());

        Call<Result> call = service.checkUserEmailExist(jsonObject);

        /*
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    if (!response.body().getError()) {
                        Log.d("-------------", response.body().getError()+"");
                        Log.d("-------------", response.body().getMessage()+"");
                        Log.d("-------------", response.body().getResponseCode()+"");
                        if(response.body().getResponseCode() == 1) {
                            JsonArray array = response.body().getData();
                            JsonObject getUSer = array.get(0).getAsJsonObject();
                            String password = getUSer.get("password").getAsString();

                            if(password != oldPass){
                                Toast.makeText(ChangePassActivity.this, "Wrong pass", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(newPass != reNewPass) {
                                Toast.makeText(ChangePassActivity.this, "New pass not equal", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("password",newPass);

                            Call<Result> changePassCall = service.updatePassword(jsonObject);

                            changePassCall.enqueue(new Callback<Result>() {
                                @Override
                                public void onResponse(Call<Result> call, Response<Result> response) {
                                    if(response.body().getResponseCode() == 1){
                                        Toast.makeText(ChangePassActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Result> call, Throwable t) {
                                    Toast.makeText(ChangePassActivity.this, "fail", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else{
                            Toast.makeText(ChangePassActivity.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("-------------", response.body().getError()+"");
                        Log.d("-------------", response.body().getMessage()+"");
                    }
                } catch (Exception e){
                    Toast.makeText(ChangePassActivity.this, "Cant get response body error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage()+"");
                Toast.makeText(ChangePassActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

         */
    }




}
