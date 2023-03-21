package com.example.baostore.Api;

import static com.example.baostore.Constant.Constants.*;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baostore.Utils.AppConstant;
import com.example.baostore.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;



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

    public static void setUserLoginToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstant.USER_LOGIN_TOKEN, token).apply();
    }

    public static String getUserLoginToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.USER_LOGIN_TOKEN, null);
    }

    public static void setUserLoginState(Boolean state) {
        // xet trang thai dang nhap user
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstant.IS_USER_LOGIN, state).apply();
    }

    public static boolean getUserLoginState() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppConstant.IS_USER_LOGIN, false);
    }
}
