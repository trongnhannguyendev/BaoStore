package com.example.baostore.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public String priceToString(double price) {

        Locale locale = new Locale("vi", "VN");
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(locale);

        return priceFormat.format(price);
    }

    public Date SringToDate(String date) {
        try {
            Date date1 = new SimpleDateFormat("YYYY-mm-dd").parse(date);
            return date1;
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    public boolean checkEmailFormat(String email) {
        String pattern = "^(.+)@(\\S+)$";
        return email.matches(pattern);
    }

    public boolean isNumeric(String toNum) {
        try {
            int newNum = Integer.parseInt(toNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Bitmap loadImageFromURL(String link) {
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public String logEnqueueMsg(boolean callError, int responseCode, String callMsg){
        return "\nerror: "+callError+"\nResponse code: " +responseCode+ "\nMessage: "+callMsg;
    }


}
