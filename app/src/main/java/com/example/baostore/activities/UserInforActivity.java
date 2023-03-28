package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.testapi.RetrofitCallBack.userUpdateInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class UserInforActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView tvTitleHeader;
    ImageView imgBack;
    EditText edfullname, edPhoneNumber, edAddress, edEmail;
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
        edAddress = findViewById(R.id.edAddress_info);
        edEmail = findViewById(R.id.edMail_info);
        btnConfirm = findViewById(R.id.btnConfirm_UserInfor);
        spnAddress = findViewById(R.id.spnAddress_info);

        service = GetRetrofit.getInstance().createRetrofit();

        bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey(ADDRESS_LIST)){
            addressList = (List<Address>) bundle.getSerializable(ADDRESS_LIST);
            for(Address address1: addressList){
                Log.d("----UserInforActivity", address1.getLocation());
            }
            Log.d("---",addressList.toString());

            AddressSpinnerAdapter adapter = new AddressSpinnerAdapter(this, addressList);
            spnAddress.setAdapter(adapter);

        }



        user = SharedPrefManager.getInstance(this).getUser();
        Log.d("----", "id: "+user.getUserid()+" fullname: " + user.getFullname()+ " phone number: "+ user.getPhonenumber());

        edfullname.setText(user.getFullname());
        edPhoneNumber.setText(user.getPhonenumber());
        edEmail.setText(user.getEmail());
        edAddress.setText("");
        edAddress.setEnabled(false);
        //header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Thông tin cá nhân");

        //button back
        imgBack = findViewById(R.id.back_button);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(view ->{
            String fullName = edfullname.getText().toString().trim();
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            String address = edAddress.getText().toString().trim();
            String email = edEmail.getText().toString().trim();

            if(fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else{
                if(!fullName.equals(user.getFullname())){
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    object.addProperty(USER_FULL_NAME, fullName);
                    Call<UserResponse> call = service.updateFullname(object);

                    call.enqueue(userUpdateInfo(this));
                }
                if(!phoneNumber.equals(user.getPhonenumber())  && phoneNumber.length() == 10){
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_EMAIL, user.getEmail());
                    object.addProperty(USER_PHONE_NUMBER, phoneNumber);
                    Call<UserResponse> call = service.updatePhoneNumber(object);

                    call.enqueue(userUpdateInfo(this));
                }
                // TODO add update address list

                if(!email.equals(user.getEmail())){
                    updateEmail(email);
                }
            }
        });
    }

    public void updateEmail(String newEmail){
        JsonObject object = new JsonObject();
        object.addProperty(USER_EMAIL, newEmail);

        Call<UserResponse> call = service.updateEmail(object);

        call.enqueue(userUpdateInfo(this));

    }



}