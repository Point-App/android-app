package com.easy.pointapp.model.api.v1;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.PointRestService;

import android.content.Context;
import android.location.Location;

import java.util.List;

import rx.Observable;

/**
 * Created by Igor on 01.07.2015.
 */
public class PostsLoader extends RestClient {

    public static Observable<Void> like(Context context, String postID) {
        return RestClient.getService().sendLike(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("type", "post").addValue("target", postID).build());
    }

    public static Observable<Post> loadSingle(Context context, String postID) {
        return RestClient.getService().loadPost(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).build());
    }

    public static Observable<List<Post>> loadChosenPosts(Context context) {
        return RestClient.getService().loadFavouritePosts(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .build());
    }

    public static Observable<List<Post>> loadPosts(Context context, Location location) {
        return RestClient.getService().loadPosts(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).build());
    }

    public static Observable<Void> sendPost(Context context, Location location, String body) {
        return RestClient.getService().sendPost(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).addValue("text", body).build());
    }
}
