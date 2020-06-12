package com.laonstory.laon_dev01.gimcarry_map.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.laonstory.laon_dev01.gimcarry_map.ManuActivity;
import com.laonstory.laon_dev01.gimcarry_map.R;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    NotificationCompat.Builder builder;
    NotificationManager manager;
    NotificationChannel channel;

    @Override
    public void onNewToken(String s) {
        Log.e("token_refresh ", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String msg = remoteMessage.getData().get("message");
        sendNotification(msg);
    }

    public void sendNotification(String msg){
        Intent intent = new Intent(this, ManuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            channel = new NotificationChannel("gimcarry", "gimcarry", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel description");
            channel.enableLights(true);
            channel.setLightColor(R.color.orange);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this)
                    .setContentTitle("짐캐리")
                    .setContentText(msg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setSmallIcon(R.drawable.icon_logo_2)
                    .setAutoCancel(true)
                    .setChannelId("gimcarry")
                    .setSound(defaultsounduri)
                    .setContentIntent(pendingIntent);
        } else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.icon_logo_2)
                    .setContentTitle("짐캐리")
                    .setContentText(msg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setAutoCancel(true)
                    .setSound(defaultsounduri)
                    .setContentIntent(pendingIntent);
        }


        manager.notify(0, builder.build());
    }
}
