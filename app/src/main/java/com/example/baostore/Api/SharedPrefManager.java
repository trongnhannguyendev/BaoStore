package com.example.baostore.Api;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.USER_STATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.baostore.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "userPref";
    private static final String USER_IMAGE = "user_image";


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, user.getUserid());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_FULL_NAME, user.getFullname());
        editor.putString(USER_PHONE_NUMBER, user.getPhonenumber());
        editor.putInt(USER_STATE, user.getState());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setUserid(sharedPreferences.getInt(USER_ID, 0));
        user.setEmail(sharedPreferences.getString(USER_EMAIL, null));
        user.setFullname(sharedPreferences.getString(USER_FULL_NAME, null));
        user.setPhonenumber(sharedPreferences.getString(USER_PHONE_NUMBER, null));
        user.setState(sharedPreferences.getInt(USER_STATE, 0));
        return user;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public boolean saveUserImage(String string){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_IMAGE, string);
        editor.apply();
        return true;

    }

    public String getUserImage(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_IMAGE, null);
    }

}
