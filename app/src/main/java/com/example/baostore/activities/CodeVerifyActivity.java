package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_OBJECT;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.VERIFICATION_CODE;
import static com.example.baostore.testapi.RetrofitCallBack.getUserRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class CodeVerifyActivity extends AppCompatActivity {
    EditText edCode1, edCode2, edCode3, edCode4;
    Button btnVerify;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        edCode1 = findViewById(R.id.edCode1);
        edCode2 = findViewById(R.id.edCode2);
        edCode3 = findViewById(R.id.edCode3);
        edCode4 = findViewById(R.id.edCode4);
        btnVerify = findViewById(R.id.btnVerifyCode_verify);

        int code =getIntent().getIntExtra(VERIFICATION_CODE,-11111);



        btnVerify.setOnClickListener(view->{
            String code1 = edCode1.getText().toString();
            String code2 = edCode2.getText().toString();
            String code3 = edCode3.getText().toString();
            String code4 = edCode4.getText().toString();

            int inputCode = Integer.parseInt(code1+code2+code3+code4);
            if(code == inputCode){
                Toast.makeText(this, "Verify successfully", Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                User user = (User) bundle.getSerializable(USER_OBJECT);
                int actionCode = bundle.getInt(ACTION_CODE);
                // Register
                if(actionCode == 1){
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    Call<UserResponse> call = service.checkUserEmailExist(object);
                    call.enqueue(getUserRegister(CodeVerifyActivity.this, object));
                }
                // Forgot password
                if(actionCode == 2){
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL,user.getEmail());
                    object.addProperty(USER_PASSWORD,user.getPassword());
                    object.addProperty(USER_PHONE_NUMBER,user.getPhonenumber());
                    object.addProperty(USER_FULL_NAME, user.getFullname());
                    Call<UserResponse> passCall= service.updatePassword(object);
                }
            } else{
                Toast.makeText(this, "Wrong code", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
