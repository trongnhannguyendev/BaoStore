package com.example.baostore.DAOs;

import static com.example.baostore.Constant.Constants.*;

import android.content.Context;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.models.Address;
import com.example.baostore.models.User;
import com.google.gson.JsonObject;

public class UserDAO {
    private final Context context;
    public int responseCode = -1;
    LoginActivity loginActivity;
    User user;
    Address address;


    public UserDAO(Context context) {
        this.context = context;
    }


    public void saveLoginInfo(JsonObject object) {
        int userID = object.get(USER_ID).getAsInt();
        String email = object.get(USER_EMAIL).getAsString();
        String fullName = object.get(USER_FULL_NAME).getAsString();
        String phonenumber = object.get(USER_PHONE_NUMBER).getAsString();
        user = new User();
        user.setUserid(userID);
        user.setEmail(email);
        user.setFullname(fullName);
        user.setPhoneNumber(phonenumber);

        SharedPrefManager.getInstance(context).userLogin(user);
    }


}
