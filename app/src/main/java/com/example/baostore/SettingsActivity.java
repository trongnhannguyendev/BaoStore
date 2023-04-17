package com.example.baostore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.baostore.Utils.MyLocale;
import com.example.baostore.activities.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingsActivity extends AppCompatActivity {

    Spinner spnLanguage;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.mtbSetting);
        spnLanguage = findViewById(R.id.spnLanguage_settings);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.language));
        spnLanguage.setAdapter(arrayAdapter);

        int initValue=0;
        String language = MyLocale.getLanguage(this);
        switch (language){
            case "vi":
                initValue =0;
                break;
            case "en":
                initValue = 1;
                break;
            case "fr":
                initValue = 2;
                break;

        }

        spnLanguage.setSelection(initValue, false);
        spnLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItemPosition()){
                    case 0:
                        MyLocale.setLocale(SettingsActivity.this,"vi");
                        break;
                    case 1:
                        MyLocale.setLocale(SettingsActivity.this,"en");
                        break;
                    case 2:
                        MyLocale.setLocale(SettingsActivity.this,"fr");
                        break;
                }

                recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toolbar.setNavigationOnClickListener(view->{
            finish();
        });
    }
}