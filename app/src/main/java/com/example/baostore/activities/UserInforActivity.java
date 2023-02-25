package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;

public class UserInforActivity extends AppCompatActivity {
    private  Toolbar toolbar;
    TextView tvTitleHeader;
    ImageView imgBack;
    EditText edfullname, edPhoneNumber,edAddress,edEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        edfullname = findViewById(R.id.edFullname_info);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_info);
        edAddress = findViewById(R.id.edAddress_info);
        edEmail = findViewById(R.id.edMail_info);

        User user = SharedPrefManager.getInstance(this).getUser();

        edfullname.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhoneNumber());
        edAddress.setText("Need to get");
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


}