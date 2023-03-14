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
        edPass =  findViewById(R.id.ednewPass_forgotPass);
        edRePass = findViewById(R.id.edReNewPass_forgotPass);
        btnConfirm = findViewById(R.id.btnChangePass_forgotPass);

        btnConfirm.setOnClickListener(view ->{
            String email = edEmail.getText().toString();

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
        });

    }
}