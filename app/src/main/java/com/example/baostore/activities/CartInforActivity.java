package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Address;
import com.example.baostore.models.User;

import java.util.List;

public class CartInforActivity extends AppCompatActivity {
    TextView tvTitleHeader;
    ImageView imgBack;
    private MotionButton btnConfirmCartInfor;
    private CardView cvIconProgress;
    private Utils utils;
    Bundle bundle;

    EditText edFullName, edPhoneNumber, edAddress, edEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_infor);


        // màu icon progress
        cvIconProgress = findViewById(R.id.cvProgress_2);
        int color = getResources().getColor(R.color.ic_progress);
        cvIconProgress.setCardBackgroundColor(color);


        //header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Thông tin nhận hàng");

        //button back
        imgBack = findViewById(R.id.back_button);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        // Load user
        edFullName = findViewById(R.id.edFullName_cartinfo);
        edPhoneNumber = findViewById(R.id.edPhoneNumber_cartinfo);
        edAddress = findViewById(R.id.edAddress_cartinfo);
        edEmail = findViewById(R.id.edEmail_cartinfo);

        User user = SharedPrefManager.getInstance(this).getUser();

        // Đổ địa chỉ
        bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey(ADDRESS_LIST)){
            List<Address> addressList = (List<Address>) bundle.getSerializable(ADDRESS_LIST);
            for(Address address1: addressList){
                if (address1 == addressList.get(0)){
                    edAddress.setText(address1.getLocation());
                }
                if(address1.getIsdefault() == 1){
                    edAddress.setText(address1.getLocation());
                }

            }
        } else{
            edAddress.setText("");
        }



        edFullName.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhonenumber());
        edEmail.setText(user.getEmail());

// xử lý button
        btnConfirmCartInfor = findViewById(R.id.btnConfirmCartInfor);
        btnConfirmCartInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String phoneNumber = edPhoneNumber.getText().toString().trim();
                String fullName = edFullName.getText().toString().trim();
                String address = edAddress.getText().toString().trim();

                if(checkError(email, phoneNumber, fullName)){
                    saveReceiver(address, phoneNumber,fullName);
                }



            }
        });

    }

    public void saveReceiver(String address, String phoneNumber, String fullName){
        Intent i = new Intent(CartInforActivity.this, CartPaymentActivity.class);
        bundle.putString(ADDRESS_LOCATION, address);
        bundle.putString(USER_PHONE_NUMBER, phoneNumber);
        bundle.putString(USER_FULL_NAME, fullName);
        i.putExtras(bundle);

        startActivity(i);
    }

     private boolean checkError(String email, String phoneNumber, String fullname) {
        utils = new Utils();

        boolean noError = true;

        if (phoneNumber.isEmpty()) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_empty));
            noError = false;
        } else if (phoneNumber.length() != 10) {
            edPhoneNumber.setError(getResources().getString(R.string.err_phonenumber_format));
            noError = false;
        } else if (!utils.isNumeric(phoneNumber)) {
            edPhoneNumber.setError(getResources().getString(R.string.err_num_format));
            noError = false;
        }

        if (email.isEmpty()) {
            edEmail.setError(getResources().getString(R.string.err_email_empty));
            noError = false;
        } else if (!utils.checkEmailFormat(email)) {
            edEmail.setError(getResources().getString(R.string.err_num_format));
            noError = false;
        }

        if (fullname.isEmpty()) {
            edFullName.setError(getResources().getString(R.string.err_fullname_empty));
            noError = false;
        }
        return noError;
    }

}