package com.example.baostore.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baostore.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "userPref";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_FULLNAME = "keyuserfullname";
    private static final String KEY_USER_PHONENUMBER = "keyuserphonenumber";

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
        editor.putInt(KEY_USER_ID, user.getUserID());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_FULLNAME, user.getFullname());
        editor.putString(KEY_USER_PHONENUMBER, user.getPhoneNumber());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_FULLNAME, null),
                sharedPreferences.getString(KEY_USER_PHONENUMBER, null)

        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}