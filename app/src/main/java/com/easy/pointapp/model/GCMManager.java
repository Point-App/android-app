package com.easy.pointapp.model;

import com.easy.pointapp.model.api.v1.RestClient;

import android.content.Context;

import rx.Observable;

/**
 * Created by mini1 on 24.07.15.
 */
public class GCMManager extends RestClient {

    public static Observable<Void> registerDevice(Context context, String pushToken) {
        return RestClient.getService().pushRegister(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("platform", "gcm").addValue("push_token", pushToken).build());
    }
}
