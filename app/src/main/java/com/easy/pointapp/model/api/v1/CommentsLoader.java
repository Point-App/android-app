package com.easy.pointapp.model.api.v1;

import android.content.Context;
import android.util.Log;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.system.DeviceInformationManager;
import com.easy.pointapp.model.Routes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mini1 on 03.07.15.
 */
public class CommentsLoader extends RestClient {
    public boolean like(Context context,Comment comment)
    {
        boolean result = false;
        LikeRequest request = new LikeRequest();

        request.app_v = DeviceInformationManager.getAppVersion();
        request.dev_model = DeviceInformationManager.getDeviceModel();
        request.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
        request.user = AuthManager.getAuthToken(context);
        request.type = "comment";
        request.target = comment._id;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(request);
        try{ 

            String jsonResponse = this.post(Routes.LIKE,json);
            Log.d("posts", jsonResponse);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public List<Comment> loadComments(Context context,String post)
    {
        List<Comment> result = new ArrayList<Comment>();
        CommentShowRequest showRequest = new CommentShowRequest();
        showRequest.app_v = DeviceInformationManager.getAppVersion();
        showRequest.dev_model = DeviceInformationManager.getDeviceModel();
        showRequest.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
        showRequest.post = post;
        showRequest.user = AuthManager.getAuthToken(context);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()

                .create();
        String json = gson.toJson(showRequest);
        try{

            String jsonResponse = this.post(Routes.SHOW_COMMENTS,json);
            Log.d("posts",jsonResponse);
            Type collectionType = new TypeToken<List<Comment>>() {
            }.getType();
            result = gson.fromJson(jsonResponse,collectionType);
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createComment(Context context,String post,String text)
    {
        CommentRequest createRequest = new CommentRequest();
        createRequest.post = post;
        createRequest.app_v = DeviceInformationManager.getAppVersion();
        createRequest.dev_model = DeviceInformationManager.getDeviceModel();
        createRequest.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
        createRequest.user = AuthManager.getAuthToken(context);
        createRequest.text = text;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()

                .create();
        String json = gson.toJson(createRequest);
        try{

            String jsonResponse = this.post(Routes.CREATE_COMMENT,json);
            Log.e("create response",jsonResponse);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
