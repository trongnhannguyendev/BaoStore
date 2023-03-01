package com.example.baostore.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.baostore.R;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public void createSnackBar(View v, String msg){
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public String priceToString(double price){

        Locale locale = new Locale("vi", "VN");
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(locale);

        return  priceFormat.format(price);
    }

    public Date SringToDate(String date){
        try {
            Date date1 = new SimpleDateFormat("YYYY-mm-dd").parse(date);
            return date1;
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    public boolean checkEmailFormat(String email){
        String pattern = "^(.+)@(\\S+)$";
        return email.matches(pattern);
    }

    public boolean isNumeric(String toNum){
        try {
            int newNum = Integer.parseInt(toNum);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Bitmap loadImageFromURL(String link){
        URL url;
        Bitmap bmp = null;
        try{
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }


}
