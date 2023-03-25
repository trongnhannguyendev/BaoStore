package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.VERIFICATION_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.R;

public class EmailSentActivity extends AppCompatActivity {
    EditText edVerify;
    Button btnVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sent);
        edVerify = findViewById(R.id.edVerifyCode_verify);
        btnVerify = findViewById(R.id.btnVerifyCode_verify);

        int code =getIntent().getIntExtra(VERIFICATION_CODE,-11111);

        btnVerify.setOnClickListener(view->{
            int inputCode = Integer.parseInt(edVerify.getText().toString().trim());
            if(code == inputCode){
                Toast.makeText(this, "Verify successfully", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Wrong code", Toast.LENGTH_SHORT).show();
            }
        });


    }
}