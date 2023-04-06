package com.example.baostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    Button btnLime, btnGreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnLime = findViewById(R.id.btnLime);
        btnGreen =findViewById(R.id.btnGreen);

        btnLime.setOnClickListener(view->{
            getTheme().applyStyle(R.style.OverlayThemeLime, true);
            setContentView(R.layout.activity_settings);
        });
        btnGreen.setOnClickListener(view->{
            getTheme().applyStyle(R.style.OverlayThemeGreen, true);
            recreate();
        });
    }
}