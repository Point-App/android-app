package com.easy.pointapp.vcs;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import com.easy.pointapp.model.GCMManager;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mini1 on 24.07.15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    private static final String[] CHANNELS = {"global"};

    private static final String gcm_defaultSenderId = "330463985722";

    private static final String SENT_TOKEN_TO_SERVER = "token_sent_to_server";

    private static final String REGISTRATION_COMPLETE = "registration_complete";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID
                        .getToken(gcm_defaultSenderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                                null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);

                sendRegistrationToServer(token);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side
     * account
     * maintained by your application.
     *
     * @param pushToken The new token.
     */
    private void sendRegistrationToServer(String pushToken) {
        // Add custom implementation, as needed.
        Log.d("GCM_Token", pushToken);

        GCMManager.registerDevice(this, pushToken).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.d("PUSH", "Registration succeeded");
                PreferenceManager.getDefaultSharedPreferences(RegistrationIntentService.this).edit()
                        .putBoolean(SENT_TOKEN_TO_SERVER, true).commit();

                LocalBroadcastManager.getInstance(RegistrationIntentService.this)
                        .sendBroadcast(new Intent(REGISTRATION_COMPLETE));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                PreferenceManager.getDefaultSharedPreferences(RegistrationIntentService.this).edit()
                        .putBoolean(SENT_TOKEN_TO_SERVER, false).commit();
            }
        });
    }
}
