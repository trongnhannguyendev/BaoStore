package com.example.baostore.testapi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.baostore.R;
import com.example.baostore.activities.SplashActivity;

public class AppHelper {
    public static void pushNotification(Context context, String title, String message){
        Intent notIntent = new Intent(context.getApplicationContext(), SplashActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(context, 0,
                notIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationChannel channel = new NotificationChannel("123", "BAOSTORE", NotificationManager.IMPORTANCE_DEFAULT);

        Intent fullScreenIntent = new Intent(context, SplashActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"123")
                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendInt)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setFullScreenIntent(fullScreenPendingIntent, true);

        NotificationManager notificationManager =
                (NotificationManager) context. getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager!=null){
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(123,notificationBuilder.build());
        }
    }
}
