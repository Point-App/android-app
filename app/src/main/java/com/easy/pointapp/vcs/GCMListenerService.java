package com.easy.pointapp.vcs;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.NotificationMessage;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

/**
 * Created by mini1 on 24.07.15.
 */
public class GCMListenerService extends GcmListenerService {

    private static final String TAG = "GcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("aps");
        NotificationMessage response = new Gson().fromJson(message,NotificationMessage.class);
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + response.alert);
        Intent intent = new Intent(this, PostsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(response.alert)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
