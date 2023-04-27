package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.adapters.AddressSpinnerAdapter;
import com.example.baostore.models.Address;
import com.example.baostore.models.User;

import java.util.ArrayList;
import java.util.List;

public class CartInforActivity extends AppCompatActivity {
    EditText edFullName, edPhoneNumber, edEmail;
    TextView tvTitleHeader;
    ImageView imgBack;
    Spinner spnAddress;
    private MotionButton btnConfirmCartInfor;
    private CardView cvIconProgress;

    private Utils utils;
    Bundle bundle;
    List<Address> addressList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cart_infor);

            tvTitleHeader = findViewById(R.id.title);
            imgBack = findViewById(R.id.back_button);
            cvIconProgress = findViewById(R.id.cvProgress_2);

            edFullName = findViewById(R.id.edFullName_cartinfo);
            edPhoneNumber = findViewById(R.id.edPhoneNumber_cartinfo);
            edEmail = findViewById(R.id.edEmail_cartinfo);
            btnConfirmCartInfor = findViewById(R.id.btnConfirmCartInfor);
            spnAddress = findViewById(R.id.spnAddresses_cartinfo);

            addressList = new ArrayList<>();

            utils = new Utils();

            // màu icon progress
            int color = getResources().getColor(R.color.ic_progress);
            cvIconProgress.setCardBackgroundColor(color);

            //header
            tvTitleHeader.setText(getResources().getString(R.string.header_cart_info));

            //button back

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            // Load user
            User user = SharedPrefManager.getInstance(this).getUser();

            // Đổ địa chỉ
            bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey(ADDRESS_LIST)) {
                addressList = (List<Address>) bundle.getSerializable(ADDRESS_LIST);
                for (Address address1 : addressList) {
                    Log.d(getString(R.string.debug_UserInforActivity), address1.getLocation());
                }
                Address address1 = new Address();
                address1.setLocation(getString(R.string.text_spinner_new_address));
                addressList.add(address1);
                Toast.makeText(this, "size: " + addressList.size(), Toast.LENGTH_SHORT).show();
                AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(this, addressList);
                spnAddress.setAdapter(adapter);

                edFullName.setText(user.getFullname());
                edPhoneNumber.setText(user.getPhonenumber());
                edEmail.setText(user.getEmail());
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
                    Log.d(getString(R.string.debug_UserInforActivity), "position: " + i + "size: " + addressList.size());
                    if (i == addressList.size() - 1) {
                        Intent intent = new Intent(CartInforActivity.this, AddAddressActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    int somethingid = adapterView.getSelectedItemPosition();
                    if (somethingid == addressList.size() - 1) {
                        Intent intent = new Intent(CartInforActivity.this, AddAddressActivity.class);
                        startActivity(intent);
                    }
                }
            });

// xử lý button
            btnConfirmCartInfor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = edEmail.getText().toString().trim();
                    String phoneNumber = edPhoneNumber.getText().toString().trim();
                    String fullName = edFullName.getText().toString().trim();
                    Address address2 = (Address) spnAddress.getSelectedItem();
                    String address = address2.getLocation();

                    if (!checkError(email, phoneNumber, fullName)) {
                        Intent i = new Intent(CartInforActivity.this, CartPaymentActivity.class);
                        bundle.putString(ADDRESS_LOCATION, address);
                        bundle.putString(USER_PHONE_NUMBER, phoneNumber);
                        bundle.putString(USER_FULL_NAME, fullName);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
            });
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }
    }

    private boolean checkError(String email, String phoneNumber, String fullname) {

        boolean hasError = false;

        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_empty));
            hasError = true;
        } else if (phoneNumber.length() != 10) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_format));
            hasError = true;
        } else if (!utils.isNumeric(phoneNumber)) {
            edPhoneNumber.setError(getResources().getString(R.string.err_num_format));
            hasError = true;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.err_email_empty));
            hasError = true;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.err_num_format));
            hasError = true;
        }

        if (fullname.isEmpty()) {
            edFullName.setError(getResources().getString(R.string.err_fullname_empty));
            hasError = true;
        }
        return hasError;
    }

}