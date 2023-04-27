package com.example.baostore.activities;

import static com.example.baostore.Api.RetrofitCallBack.addressNoData;
import static com.example.baostore.Constant.Constants.ADDRESS_CITY;
import static com.example.baostore.Constant.Constants.ADDRESS_DISTRICT;
import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.ADDRESS_NAME;
import static com.example.baostore.Constant.Constants.ADDRESS_WARD;
import static com.example.baostore.Constant.Constants.USER_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.responses.AddressResponse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class AddAddressActivity extends AppCompatActivity {
    EditText edAddressName;
    Spinner spnWard, spnDistrict;
    ArrayAdapter<String> wardAdapter;
    MaterialToolbar toolbar;
    Button btnAdd;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_address);
            spnDistrict = findViewById(R.id.spnDistrict);
            spnWard = findViewById(R.id.spnWard);
            edAddressName = findViewById(R.id.edAddressName);
            btnAdd = findViewById(R.id.btnAdd_address);
            toolbar = findViewById(R.id.myToolbar);

            service = GetRetrofit.getInstance().createRetrofit();

            ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.district));
            spnDistrict.setAdapter(districtAdapter);

            toolbar.setNavigationOnClickListener(view -> {
                finish();
            });

            spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_1));
                            break;
                        case 1:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_2));
                            break;
                        case 2:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_3));
                            break;
                        case 3:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_4));
                            break;
                        case 4:
                        case 5:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_5));
                            break;
                        case 6:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_7));
                            break;
                        case 7:
                        case 10:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_8));
                            break;
                        case 8:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_9));
                            break;
                        case 9:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_10));
                            break;
                        case 11:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_12));
                            break;
                        case 12:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_binhtan));
                            break;
                        case 13:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_binhthanh));
                            break;
                        case 14:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_govap));
                            break;
                        case 15:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_phunhuan));
                            break;
                        case 16:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_tanbinh));
                            break;
                        case 17:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_tanphu));
                            break;
                        case 18:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_binhchanh));
                            break;
                        case 19:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_cangio));
                            break;
                        case 20:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_cuchi));
                            break;
                        case 21:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_hocmon));
                            break;
                        case 22:
                            wardAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ward_nhabe));
                            break;
                    }
                    spnWard.setAdapter(wardAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnAdd.setOnClickListener(view -> {
                String addressName = edAddressName.getText().toString().trim();
                String ward = spnWard.getSelectedItem().toString();
                String district = spnDistrict.getSelectedItem().toString();
                String location = addressName + ", " + ward + ", " + district + ", " + "TP.HCM";

                JsonObject object = new JsonObject();
                int userId = SharedPrefManager.getInstance(this).getUser().getUserid();
                object.addProperty(USER_ID, userId);
                object.addProperty(ADDRESS_LOCATION, location);
                object.addProperty(ADDRESS_WARD, ward);
                object.addProperty(ADDRESS_DISTRICT, district);
                object.addProperty(ADDRESS_CITY, "TP.HCM");
                object.addProperty(ADDRESS_NAME, addressName);

                Call<AddressResponse> call = service.insertUserAddress(object);
                call.enqueue(addressNoData(this, 1));

            });
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }
    }
}