package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.ApiUrl;
import com.example.baostore.Api.Result;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.Address;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInforActivity extends AppCompatActivity {
    private  Toolbar toolbar;
    TextView tvTitleHeader;
    ImageView imgBack;
    EditText edfullname, edPhoneNumber,edAddress,edEmail;
    Address address;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        edfullname = findViewById(R.id.edFullname_info);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_info);
        edAddress = findViewById(R.id.edAddress_info);
        edEmail = findViewById(R.id.edMail_info);

        user = SharedPrefManager.getInstance(this).getUser();

        getUserMainAddress();

        edfullname.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhoneNumber());
        //edAddress.setText(address.getAddressName());
        edEmail.setText(user.getEmail());

        //header
        tvTitleHeader=findViewById(R.id.title);
        tvTitleHeader.setText("Thông tin cá nhân");

        //button back
        imgBack=findViewById(R.id.back_button);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


    }

    public void getUserMainAddress(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        JsonObject jsonObject = new JsonObject();

        int id = user.getUserID();
        jsonObject.addProperty("userID",id);
        Log.d("----------------- Json object: ", jsonObject+"");

        Call<Result> call = service.getAddressByUser(jsonObject);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Log.d("----------------", response.body()+"");
                    if (!response.body().getError()) {
                        Log.d("-------------", response.body().getError() + "");
                        Log.d("-------------", response.body().getMessage() + "");
                        Log.d("-------------", response.body().getResponseCode() + "");
                        if (response.body().getResponseCode() == 1) {
                            /*
                            JsonArray myArr = response.body().getData();
                            JsonObject jsonObject1 = myArr.get(0).getAsJsonObject();

                            int id = jsonObject1.get("addressid").getAsInt();
                            String addressLocation = jsonObject1.get("location").getAsString();
                            address = new Address(id, addressLocation);
                            edAddress.setText(address.getAddressLocation());



                             */
                        } else {
                            Toast.makeText(UserInforActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("-------------", response.body().getError() + "");
                        Log.d("-------------", response.body().getMessage() + "");
                    }
                } catch (Exception e) {
                    Toast.makeText(UserInforActivity.this, "Cant get response body error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("-------------", t.getMessage() + "");
                Toast.makeText(UserInforActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }


}