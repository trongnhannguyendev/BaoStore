package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baostore.DAOs.TempUserDAO;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;

public class RegisterActivity extends AppCompatActivity {
    EditText edEmail, edPass, edRePass, edPhoneNumber, edFullname;
    MotionButton btnCancel, btnRegister;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        utils = new Utils();

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Hủy
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Đăng ký
        btnRegister = findViewById(R.id.btnRegister);
        edEmail = findViewById(R.id.edEmail_reg);
        edPass = findViewById(R.id.edPassword_reg);
        edRePass = findViewById(R.id.edRePassword_reg);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_reg);
        edFullname = findViewById(R.id.edFullname_reg);

        btnRegister.setOnClickListener(view -> {
            String email = edEmail.getText().toString();
            String pass = edPass.getText().toString();
            String pass_re = edRePass.getText().toString();
            String phoneNumber = edPhoneNumber.getText().toString();
            String fullname = edFullname.getText().toString();

            if(checkError(email, pass, pass_re, phoneNumber, fullname)){
                TempUserDAO dao = new TempUserDAO(this);
                dao.register(email, pass, fullname, phoneNumber);
            } else{
                Toast.makeText(this, "Còn lỗi trong form", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean checkError(String email, String pass, String pass_re, String phoneNumber, String fullname) {
        boolean noError = true;

        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.no_pass));
            noError = false;
        }
        if (!pass.equals(pass_re)) {
            edRePass.setError(getResources().getString(R.string.pass_not_equal_repass));
            noError = false;
        }
        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.no_phonenumber));
            noError = false;
        } else if(!utils.isNumeric(phoneNumber)){
            edPhoneNumber.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.no_email));
            noError = false;
        } else if(!utils.checkEmailFormat(email)){
            edEmail.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if(fullname.isEmpty()){
            edFullname.setError(getResources().getString(R.string.no_fullname));
            noError = false;
        }
        if (noError){
            return true;
        }
        return false;
    }
}






