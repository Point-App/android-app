package com.easy.pointapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.easy.pointapp.model.api.v1.ApiVersion;

import org.apache.http.auth.AUTH;

/**
 * Created by Igor on 28.06.2015.
 */
public class AuthManager {
    private static final String AUTH_TOKEN = "authorization_token";
    private static final String AUTH_VERSION = "authorization_version";

    public static String getAuthToken(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(ApiVersion.apiVersion.equals(settings.getString(AUTH_VERSION,null)))
        {
            String token = settings.getString(AUTH_TOKEN, null);

            if(token==null)
            {
                Log.d("Device token","null");
            }
            else
            {
                Log.d("Device_Token",token);
            }

            return token;
        }
        else
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(AUTH_TOKEN);
            editor.putString(AUTH_VERSION,ApiVersion.apiVersion);
            editor.commit();
            return null;
        }

    }
    public static void setAuthToken(Context context,String authToken)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(AUTH_TOKEN, authToken);
        editor.commit();
    }
}
