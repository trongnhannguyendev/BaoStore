package com.example.baostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ChangePassActivity extends AppCompatActivity {
    MaterialButton btnChange;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        btnChange = findViewById(R.id.btnChangePass);
        toolbar = findViewById(R.id.myToolbar);

        btnChange.setOnClickListener(view ->{finish();});

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}