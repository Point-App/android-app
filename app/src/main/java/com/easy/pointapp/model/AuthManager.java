package com.easy.pointapp.model;

import com.easy.pointapp.BuildConfig;
import com.easy.pointapp.model.api.v1.ApiVersion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Igor on 28.06.2015.
 */
public class AuthManager {

    private static final String AUTH_TOKEN = "authorization_token";

    private static final String AUTH_VERSION = "authorization_version";

    public static String getAuthToken(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (ApiVersion.apiVersion.equals(settings.getString(AUTH_VERSION, null))) {
            String token = settings.getString(AUTH_TOKEN, null);
            if (BuildConfig.DEBUG && !TextUtils.isEmpty(token)) {
                Log.d("Device Token", token);
            }
            return token;
        } else {
            settings.edit().remove(AUTH_TOKEN).putString(AUTH_VERSION, ApiVersion.apiVersion)
                    .commit();
            return null;
        }

    }

    public static void setAuthToken(Context context, String authToken) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(AUTH_TOKEN, authToken).commit();
    }
}
