package com.easy.pointapp.model;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.easy.pointapp.model.api.v1.GCMRequest;
import com.easy.pointapp.model.api.v1.PostCreateRequest;
import com.easy.pointapp.model.api.v1.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by mini1 on 24.07.15.
 */
public class GCMManager extends RestClient {
    public boolean registerDevice(Context context,String token)
    {
        GCMRequest gcmRequest = new GCMRequest();
        gcmRequest.push_token = token;
        gcmRequest.user = AuthManager.getAuthToken(context);
        gcmRequest.platform = "gcm";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(gcmRequest);
        try{

            String jsonResponse = this.post(Routes.REGISTER_DEVICE,json);
            Log.e("create response", jsonResponse);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
