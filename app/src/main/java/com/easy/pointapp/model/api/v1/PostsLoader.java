package com.easy.pointapp.model.api.v1;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.DeviceInformationManager;
import com.easy.pointapp.model.Routes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 01.07.2015.
 */
public class PostsLoader extends RestClient {
    public boolean like(Context context,Post post)
    {
        boolean result = false;
        LikeRequest request = new LikeRequest();

        request.app_v = DeviceInformationManager.getAppVersion();
        request.dev_model = DeviceInformationManager.getDeviceModel();
        request.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
        request.user = AuthManager.getAuthToken(context);
        request.type = "post";
        request.target = post._id;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(request);
        try{

            String jsonResponse = this.post(Routes.LIKE,json);
            Log.d("posts",jsonResponse);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public Post loadSingle(Context context, Post viewedPost)
    {
        try{
            Post post = new Post();
            SinglePostShowRequest singlePostShowRequest = new SinglePostShowRequest();
            singlePostShowRequest.user = AuthManager.getAuthToken(context);
            singlePostShowRequest.post = viewedPost._id;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(singlePostShowRequest);
            String jsonResponse = this.post(Routes.SINGLE_POST, json);
            Log.d("loaded post",jsonResponse);
            Log.d("chosen posts",jsonResponse);
            Type collectionType = new TypeToken<List<Post>>() {}.getType();
            List<Post> result = new Gson().fromJson(jsonResponse,collectionType);
            return result.get(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public List<Post> loadChosenPosts(Context context)
    {
        try{
            List<Post> result = new ArrayList<Post>();
            ChosenPostsShowRequest chosenPostsShowRequest = new ChosenPostsShowRequest();
            chosenPostsShowRequest.app_v = DeviceInformationManager.getAppVersion();
            chosenPostsShowRequest.dev_model = DeviceInformationManager.getDeviceModel();
            chosenPostsShowRequest.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                    DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
            chosenPostsShowRequest.user = AuthManager.getAuthToken(context);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(chosenPostsShowRequest);


            String jsonResponse = this.post(Routes.SHOW_CHOSENS,json);
            Log.d("chosen posts",jsonResponse);
            Type collectionType = new TypeToken<List<Post>>() {
            }.getType();
            result = new Gson().fromJson(jsonResponse,collectionType);
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public List<Post> loadPosts(Context context,Location location)
    {
        List<Post> result = new ArrayList<Post>();
        try{
            PostsShowRequest showRequest = new PostsShowRequest();
            showRequest.lat = ""+location.getLatitude();
            showRequest.lon = ""+location.getLongitude();
            showRequest.app_v = DeviceInformationManager.getAppVersion();
            showRequest.dev_model = DeviceInformationManager.getDeviceModel();
            showRequest.dev_os = "OS: android : "+DeviceInformationManager.getOSVersion()+" : "+
                    DeviceInformationManager.getSDKName()+" : sdk="+DeviceInformationManager.getSDKVersion();
            showRequest.user = AuthManager.getAuthToken(context);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String json = gson.toJson(showRequest);
            String jsonResponse = this.post(Routes.SHOW_POSTS,json);
            Log.d("posts",jsonResponse);
            Type collectionType = new TypeToken<List<Post>>() {
            }.getType();
            result = new Gson().fromJson(jsonResponse,collectionType);
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createPost(Context context,Location location,String text)
    {
        try{
            PostCreateRequest createRequest = new PostCreateRequest();
            createRequest.lat = ""+location.getLatitude();
            createRequest.lon = ""+location.getLongitude();
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


            String jsonResponse = this.post(Routes.CREATE_POST,json);
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
