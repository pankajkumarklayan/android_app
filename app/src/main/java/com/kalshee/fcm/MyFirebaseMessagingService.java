package com.kalshee.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kalshee.MainActivity;
import com.kalshee.R;
import com.kalshee.fragment.ChatFragment;
import com.kalshee.utill.MyApp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eWeb_A1 on 7/9/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    String TAG="MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String response = remoteMessage.getData().get("message");
        Log.e(TAG, "===========onMessageReceived============");


        try {
            JSONObject mJsonObject= new JSONObject(response);
            String notification_type= mJsonObject.getString("notification_type");
            if(notification_type.equalsIgnoreCase("Chat"))
            {

                if(MainActivity.mainActivity !=null)
                {

                  String addId= mJsonObject.getString("addId");
                  String  userName= mJsonObject.getString("userName");


                  if(ChatFragment.mOpen)
                  {


                  }
                  else
                  {
                     Intent mIntent= new Intent();
                      mIntent.putExtra("TYPE", "chat");
                      mIntent.setAction("MAINACTIVITY");
                      sendBroadcast(mIntent);

                      sendNotification("Chat");

                  }



                }
                else
                {

                    sendNotification("Chat");
                }



            }
            else
            {



            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);

        Log.e(TAG, "============NEWToken========="+s);
    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "123";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}


