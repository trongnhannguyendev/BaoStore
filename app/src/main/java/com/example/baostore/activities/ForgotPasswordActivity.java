package com.example.baostore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.baostore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

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
                // TODO: add url
                ActionCodeSettings actionCodeSettings =
                        ActionCodeSettings.newBuilder()
                                .setHandleCodeInApp(true)
                                .setUrl("sth")
                                .setAndroidPackageName(
                                        "com.example.baostore",
                                        true, /* installIfNotAvailable */
                                        "12"    /* minimumVersion */)
                                .build();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendSignInLinkToEmail(email, actionCodeSettings)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("--ForgotPasswordActivity", "Email sent.");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("--ForgotPasswordActivity", "Error signing in with email link");
                            }
                        });
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