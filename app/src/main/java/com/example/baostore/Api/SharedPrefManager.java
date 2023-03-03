package com.example.baostore.Api;

import static com.example.baostore.Constant.Constants.*;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baostore.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "userPref";


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
        editor.putInt(USER_ID, user.getUserID());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_FULL_NAME, user.getFullname());
        editor.putString(USER_PHONE_NUMBER, user.getPhoneNumber());
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
        user.setUserID(sharedPreferences.getInt(USER_ID, 0));
        user.setEmail(sharedPreferences.getString(USER_EMAIL, null));
        user.setFullname(sharedPreferences.getString(USER_FULL_NAME, null));
        user.setPhoneNumber(sharedPreferences.getString(USER_PHONE_NUMBER, null));
        return user;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


}
