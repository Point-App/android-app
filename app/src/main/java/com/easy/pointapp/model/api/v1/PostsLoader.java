package com.easy.pointapp.model.api.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.PointRestService;
import com.easy.pointapp.model.Routes;
import com.easy.pointapp.model.system.DeviceInformationManager;

import android.content.Context;
import android.location.Location;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;

/**
 * Created by Igor on 01.07.2015.
 */
public class PostsLoader extends RestClient {

    public boolean like(Context context, String postID) {
        boolean result = false;
        LikeRequest request = new LikeRequest();

        request.app_v = DeviceInformationManager.getAppVersion();
        request.dev_model = DeviceInformationManager.getDeviceModel();
        request.dev_os = "OS: android : " + DeviceInformationManager.getOSVersion() + " : " +
                DeviceInformationManager.getSDKName() + " : sdk=" + DeviceInformationManager
                .getSDKVersion();
        request.user = AuthManager.getAuthToken(context);
        request.type = "post";
        request.target = postID;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(request);
        try {

            Reader jsonResponse = this.post(Routes.LIKE, json);
//            Log.d("posts", jsonResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Post loadSingle(Context context, Post viewedPost) {
        try {
            Post post = new Post();
            SinglePostShowRequest singlePostShowRequest = new SinglePostShowRequest();
            singlePostShowRequest.user = AuthManager.getAuthToken(context);
            singlePostShowRequest.post = viewedPost.getID();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(singlePostShowRequest);

            Reader jsonResponse = this.post(Routes.SINGLE_POST, json);
//            Log.d("loaded post", jsonResponse);
//            Log.d("chosen posts", jsonResponse);
            Type collectionType = new TypeToken<List<Post>>() {
            }.getType();
            List<Post> result = new Gson().fromJson(jsonResponse, collectionType);
            return result.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Observable<List<Post>> loadChosenPosts(Context context) {
        return RestClient.getService().getFavouritePosts(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .build());
    }

    public static Observable<List<Post>> loadPosts(Context context, Location location) {
        return RestClient.getService().getPosts(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).build());
    }

    public boolean createPost(Context context, Location location, String text) {
        try {
            PostCreateRequest createRequest = new PostCreateRequest();
            createRequest.lat = "" + location.getLatitude();
            createRequest.lon = "" + location.getLongitude();
            createRequest.app_v = DeviceInformationManager.getAppVersion();
            createRequest.dev_model = DeviceInformationManager.getDeviceModel();
            createRequest.dev_os = "OS: android : " + DeviceInformationManager.getOSVersion()
                    + " : " +
                    DeviceInformationManager.getSDKName() + " : sdk=" + DeviceInformationManager
                    .getSDKVersion();
            createRequest.user = AuthManager.getAuthToken(context);
            createRequest.text = text;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(createRequest);

            Reader jsonResponse = this.post(Routes.CREATE_POST, json);
//            Log.e("create response", jsonResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
