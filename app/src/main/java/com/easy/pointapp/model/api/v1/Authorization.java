package com.easy.pointapp.model.api.v1;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.RestClient;

import android.content.Context;
import android.location.Location;

import rx.Observable;

/**
 * Created by Igor on 30.06.2015.
 */
public class Authorization extends RestClient {

    public static Observable<AuthenticationHolder> makeAuth(Context context, Location location) {
        return RestClient.getService().register(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).build());
    }
}
