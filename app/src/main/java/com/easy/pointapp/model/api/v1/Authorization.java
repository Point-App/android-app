package com.easy.pointapp.model.api.v1;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.system.DeviceInformationManager;
import com.easy.pointapp.model.Routes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Igor on 30.06.2015.
 */
public class Authorization extends RestClient{
    public boolean makeAuth(Context context,Location location)
    {
        AuthRequest authRequest = new AuthRequest();
        authRequest.lat = ""+location.getLatitude();
        authRequest.lon = ""+location.getLongitude();
        authRequest.app_v = DeviceInformationManager.getAppVersion();
        authRequest.dev_model = DeviceInformationManager.getDeviceModel();
        authRequest.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(authRequest);
        try{

            String jsonResponse = this.post(Routes.REGISTRATION,json);
            AuthResponse response = new Gson().fromJson(jsonResponse,AuthResponse.class);
            AuthManager.setAuthToken(context,response.token);
            Log.d("token",response.token);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
