package com.example.baostore.activities;

import static com.example.baostore.Api.ApiUrl.BASE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.testapi.RetrofitCallBack.getUserRegister;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.responses.UserResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {
    EditText edEmail, edPass, edRePass, edPhoneNumber, edFullName;
    MotionButton btnCancel, btnRegister;
    Utils utils;
    ActionCodeSettings actionCodeSettings;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edEmail = findViewById(R.id.edEmail_reg);
        edPass = findViewById(R.id.edPassword_reg);
        edRePass = findViewById(R.id.edRePassword_reg);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_reg);
        edFullName = findViewById(R.id.edFullName_reg);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);

         auth = FirebaseAuth.getInstance();

         actionCodeSettings = ActionCodeSettings.newBuilder()
                .setHandleCodeInApp(true)
                 //TODO: Add link from firebase
                 .setUrl("MyLink")
                .setAndroidPackageName("com.example.baostore",true,"18")
                .build();

        // ẩn thanh pin
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Hủy
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Ánh xạ




        // Đăng ký
        btnRegister.setOnClickListener(view -> {
            turnEditingOff();
            // Lấy dữ liệu
            String email = edEmail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            String pass_re = edRePass.getText().toString().trim();
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            String fullname = edFullName.getText().toString().trim();

            // Chạy đăng ký nếu không có lỗi
            if (checkError(email, pass, pass_re, phoneNumber, fullname)) {

                sendSigninLink(email);


                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL,email);
                object.addProperty(USER_PASSWORD,pass);
                object.addProperty(USER_PHONE_NUMBER,phoneNumber);
                object.addProperty(USER_FULL_NAME, fullname);

                ApiService service = new GetRetrofit().getRetrofit();
                Call<UserResponse> call = service.checkUserEmailExist(object);
                call.enqueue(getUserRegister(RegisterActivity.this, object));


            } else{
                turnEditingOn();
            }

        });

    }

    public void verifySigninLink(String email){
        Intent intent=getIntent();
        String emailLink = intent.getData().toString();

        if(auth.isSignInWithEmailLink(emailLink)){
            auth.signInWithEmailLink(email, emailLink).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                        //TODO: add register enqueue
                    }
                }
            });
        }
    }

    public void sendSigninLink(String email){

        auth.sendSignInLinkToEmail(email, actionCodeSettings).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Email sent! check your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkError(String email, String pass, String pass_re, String phoneNumber, String fullname) {
        utils = new Utils();

        boolean noError = true;

        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.no_pass));
            noError = false;
        } else if (pass.length() <= 6) {
            edPass.setError(getResources().getString(R.string.password_length));
            noError = false;
        }
        if (!pass.equals(pass_re)) {
            edRePass.setError(getResources().getString(R.string.pass_not_equal_repass));
            noError = false;
        }
        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.no_phonenumber));
            noError = false;
        } else if (phoneNumber.length() != 10) {
            edPhoneNumber.setError(getResources().getString(R.string.not_phone_number));
            noError = false;
        } else if (!utils.isNumeric(phoneNumber)) {
            edPhoneNumber.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.no_email));
            noError = false;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.wrong_number_format));
            noError = false;
        }

        if (fullname.isEmpty()) {
            edFullName.setError(getResources().getString(R.string.no_fullname));
            noError = false;
        }
        return noError;
    }

    public void turnEditingOff(){
        edEmail.setEnabled(false);
        edPass.setEnabled(false);
        edRePass.setEnabled(false);
        edPhoneNumber.setEnabled(false);
        edFullName.setEnabled(false);
        btnRegister.setEnabled(false);
        btnCancel.setEnabled(false);

    }

    public void turnEditingOn(){
        edEmail.setEnabled(true);
        edPass.setEnabled(true);
        edRePass.setEnabled(true);
        edPhoneNumber.setEnabled(true);
        edFullName.setEnabled(true);
        btnRegister.setEnabled(true);
        btnCancel.setEnabled(true);
    }
}



