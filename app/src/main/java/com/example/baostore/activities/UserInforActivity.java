package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.example.baostore.R;

public class UserInforActivity extends AppCompatActivity {
    private  Toolbar toolbar;
    TextView tvTitleHeader;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);


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