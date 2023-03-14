package com.example.baostore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppVariable {

    private static AppVariable instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static LocalStorageManager localStorageManager;
    private final Context context;

    private AppVariable(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AppConstant.APP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static AppVariable getInstance(Context context) {
        if (instance == null) {
            instance = new AppVariable(context);
        }
        return instance;
    }

    public LocalStorageManager getLocalStorageManager() {
        return localStorageManager;
    }

    public static class LocalStorageManager {
        public static void setUserLoginState(Boolean state) {
            // xet trang thai dang nhap user
            editor.putBoolean(AppConstant.IS_USER_LOGIN, state).apply();
        }

        public static boolean getUserLoginState() {
            return sharedPreferences.getBoolean(AppConstant.IS_USER_LOGIN, false);
        }

        public static void setUserLoginEmail(String email) {
            editor.putString(AppConstant.USER_LOGIN_EMAIL, email).apply();
        }

        public static String getUserLoginEmail() {
            return sharedPreferences.getString(AppConstant.USER_LOGIN_EMAIL, null);
        }

        public static void setUserLoginToken(String token) {
            editor.putString(AppConstant.USER_LOGIN_TOKEN, token).apply();
        }

        public static String getUserLoginToken() {
            return sharedPreferences.getString(AppConstant.USER_LOGIN_TOKEN, null);
        }
    }

}
