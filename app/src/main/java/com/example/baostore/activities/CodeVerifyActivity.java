package com.example.baostore.activities;

import static com.example.baostore.Api.RetrofitCallBack.userUpdateInfo;
import static com.example.baostore.Constant.Constants.ACTION_CODE;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_OBJECT;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.VERIFICATION_CODE;
import static com.example.baostore.Api.RetrofitCallBack.getUserRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class CodeVerifyActivity extends AppCompatActivity {
    EditText edCode1, edCode2, edCode3, edCode4;
    Button btnVerify;
    ApiService service;
    Bundle bundle;
    private boolean canExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        edCode1 = findViewById(R.id.edCode1);
        edCode2 = findViewById(R.id.edCode2);
        edCode3 = findViewById(R.id.edCode3);
        edCode4 = findViewById(R.id.edCode4);
        btnVerify = findViewById(R.id.btnVerifyCode_verify);

        service = GetRetrofit.getInstance().createRetrofit();

        int verificationCode =getIntent().getIntExtra(VERIFICATION_CODE,-11111);
        bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable(USER_OBJECT);
        int actionCode = bundle.getInt(ACTION_CODE);

        Log.d("--", "Action code: "+actionCode);

        btnVerify.setOnClickListener(view->{
            turnEditingOff();
            String code1 = edCode1.getText().toString();
            String code2 = edCode2.getText().toString();
            String code3 = edCode3.getText().toString();
            String code4 = edCode4.getText().toString();

            int inputCode = Integer.parseInt(code1+code2+code3+code4);
            if(verificationCode == inputCode){
                Toast.makeText(this, "Verify successfully", Toast.LENGTH_SHORT).show();
                Log.d("--", "Input verificationCode: "+inputCode);
                // Register
                if(actionCode == 1){
                    Toast.makeText(this, "Registering ...", Toast.LENGTH_SHORT).show();
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    object.addProperty(USER_PASSWORD, user.getPassword());
                    object.addProperty(USER_FULL_NAME, user.getFullname());
                    object.addProperty(USER_PHONE_NUMBER, user.getPhonenumber());
                    Call<UserResponse> call = service.checkUserEmailExist(object);
                    call.enqueue(getUserRegister(CodeVerifyActivity.this, object));
                }
                // Forgot password
                if(actionCode == 2){
                    Toast.makeText(this, "Change pass ...", Toast.LENGTH_SHORT).show();
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL,user.getEmail());
                    object.addProperty(USER_PASSWORD,user.getPassword());
                    Call<UserResponse> passCall= service.updatePassword(object);
                    passCall.enqueue(userUpdateInfo(CodeVerifyActivity.this,2));
                }
            } else{
                Toast.makeText(this, "Wrong verificationCode", Toast.LENGTH_SHORT).show();
                turnEditingOn();
            }

        });


    }

    public void turnEditingOff(){
        edCode1.setEnabled(false);
        edCode2.setEnabled(false);
        edCode3.setEnabled(false);
        edCode4.setEnabled(false);
        btnVerify.setEnabled(false);
    }

    public void turnEditingOn(){
        edCode1.setEnabled(true);
        edCode2.setEnabled(true);
        edCode3.setEnabled(true);
        edCode4.setEnabled(true);
        btnVerify.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
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
