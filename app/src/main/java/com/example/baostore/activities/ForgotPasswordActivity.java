package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.testapi.RetrofitCallBack.getVerificationCode;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.responses.VerificationCodeResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edEmail, edPass, edRePass;
    MotionButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edEmail = findViewById(R.id.edEmail_forgotPass);
        edPass = findViewById(R.id.ednewPass_forgotPass);
        edRePass = findViewById(R.id.edReNewPass_forgotPass);
        btnConfirm = findViewById(R.id.btnChangePass_forgotPass);

        // Change password

        btnConfirm.setOnClickListener(view -> {
            String email = edEmail.getText().toString().trim();
            String pass = edPass.getText().toString().trim();
            String rePass = edRePass.getText().toString().trim();



            if (!checkError(email, pass, rePass)) {
                Bundle bundle = new Bundle();
                ApiService service = GetRetrofit.getInstance(this).getRetrofit();;
                JsonObject object = new JsonObject();
                object.addProperty(USER_EMAIL, email);
                Call<VerificationCodeResponse> call = service.getEmailVerifyCode(object);
                call.enqueue(getVerificationCode(ForgotPasswordActivity.this));

            }
        });
    }

    public boolean checkError(String email, String pass, String rePass) {
        boolean hasError = false;
        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.no_email));
            hasError = true;
        }
        if (pass.isEmpty()) {
            edPass.setError(getResources().getString(R.string.no_pass));
            hasError = true;
        } else if (!pass.equals(rePass)) {
            edRePass.setError(getResources().getString(R.string.pass_not_equal_repass));
            hasError = true;
        }
        return hasError;
    }
}