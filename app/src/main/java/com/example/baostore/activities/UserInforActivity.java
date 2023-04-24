package com.example.baostore.activities;

import static com.example.baostore.Api.RetrofitCallBack.addressNoData;
import static com.example.baostore.Api.RetrofitCallBack.userUpdateInfo;
import static com.example.baostore.Constant.Constants.ADDRESS_CITY;
import static com.example.baostore.Constant.Constants.ADDRESS_DISTRICT;
import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.ADDRESS_NAME;
import static com.example.baostore.Constant.Constants.ADDRESS_WARD;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.adapters.AddressSpinnerAdapter;
import com.example.baostore.models.Address;
import com.example.baostore.models.User;
import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class UserInforActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView tvTitleHeader;
    ImageView imgBack;
    EditText edfullname, edPhoneNumber, edNewAddress, edEmail;
    MotionButton btnConfirm;
    Spinner spnAddress;
    List<Address> addressList;
    Address address;
    User user;
    Bundle bundle;
    ApiService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        edfullname = findViewById(R.id.edFullname_info);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_info);
        edNewAddress = findViewById(R.id.edNewAddress_info);
        edEmail = findViewById(R.id.edMail_info);
        btnConfirm = findViewById(R.id.btnConfirm_UserInfor);
        spnAddress = findViewById(R.id.spnAddress_info);

        addressList = new ArrayList<>();
        service = GetRetrofit.getInstance().createRetrofit();

        //header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText(getString(R.string.header_user_info));

        //button back
        imgBack = findViewById(R.id.back_button);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ADDRESS_LIST)) {
            addressList = (List<Address>) bundle.getSerializable(ADDRESS_LIST);
            for (Address address1 : addressList) {
                Log.d(getString(R.string.debug_UserInforActivity), address1.getLocation());
            }
            Address address1 = new Address();
            address1.setLocation(getString(R.string.text_spinner_new_address));
            addressList.add(address1);
            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(this, addressList);
            spnAddress.setAdapter(adapter);
        } else {
            Address address1 = new Address();
            address1.setLocation(getString(R.string.text_spinner_new_address));
            addressList.add(address1);
            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(this, addressList);
            spnAddress.setAdapter(adapter);
        }
        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(getString(R.string.debug_UserInforActivity), "position: " + i + " size: " + addressList.size());
                if (i == addressList.size()-1) {
                    Intent intent = new Intent(UserInforActivity.this, AddAddressActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        user = SharedPrefManager.getInstance(this).getUser();
        Log.d("----", "id: " + user.getUserid() + " fullname: " + user.getFullname() + " phone number: " + user.getPhonenumber());

        edfullname.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhonenumber());
        edEmail.setText(user.getEmail());


        edfullname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if (actionID == EditorInfo.IME_ACTION_DONE && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(UserInforActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        btnConfirm.setOnClickListener(view -> {
            String fullName = edfullname.getText().toString().trim();
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            String location = edNewAddress.getText().toString().trim();
            String email = edEmail.getText().toString().trim();

            if (fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                if (!fullName.equals(user.getFullname())) {
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    object.addProperty(USER_FULL_NAME, fullName);
                    Call<UserResponse> call = service.updateFullname(object);

                    call.enqueue(userUpdateInfo(this, 1));
                }
                if (!phoneNumber.equals(user.getPhonenumber()) && phoneNumber.length() == 10) {
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    object.addProperty(USER_PHONE_NUMBER, phoneNumber);
                    Call<UserResponse> call = service.updatePhoneNumber(object);

                    call.enqueue(userUpdateInfo(this, 1));
                }
                if (!location.isEmpty()) {
                    String[] cutOutAddress = location.split(",");

                    String ward = cutOutAddress[1];
                    String district = cutOutAddress[2];
                    String city = cutOutAddress[3];
                    String addressName = cutOutAddress[0];


                    JsonObject object = new JsonObject();
                    object.addProperty(USER_ID, user.getUserid());
                    object.addProperty(ADDRESS_LOCATION, location);
                    object.addProperty(ADDRESS_WARD, ward);
                    object.addProperty(ADDRESS_DISTRICT, district);
                    object.addProperty(ADDRESS_CITY, city);
                    object.addProperty(ADDRESS_NAME, addressName);

                    Call<AddressResponse> call = service.insertUserAddress(object);
                    call.enqueue(addressNoData(this, 0));
                }

                if (!email.equals(user.getEmail())) {
                    updateEmail(email);
                }
            }
        });
    }

    public void updateEmail(String newEmail) {
        JsonObject object = new JsonObject();
        object.addProperty(USER_EMAIL, newEmail);

        Call<UserResponse> call = service.updateEmail(object);

        call.enqueue(userUpdateInfo(this, 1));

    }


}