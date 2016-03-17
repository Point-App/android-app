package com.easy.pointapp.model.api.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.PointRestService;
import com.easy.pointapp.model.Routes;
import com.easy.pointapp.model.system.DeviceInformationManager;

import android.content.Context;

import java.io.Reader;
import java.util.List;

import rx.Observable;

/**
 * Created by mini1 on 03.07.15.
 */
public class CommentsLoader extends RestClient {

    public boolean like(Context context, Comment comment) {
        boolean result = false;
        LikeRequest request = new LikeRequest();

        request.app_v = DeviceInformationManager.getAppVersion();
        request.dev_model = DeviceInformationManager.getDeviceModel();
        request.dev_os = "OS: android : " + DeviceInformationManager.getOSVersion() + " : " +
                DeviceInformationManager.getSDKName() + " : sdk=" + DeviceInformationManager
                .getSDKVersion();
        request.user = AuthManager.getAuthToken(context);
        request.type = "comment";
        request.target = comment.getID();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(request);
        try {

            Reader jsonResponse = this.post("like", json);
//            Log.d("posts", jsonResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Observable<List<Comment>> loadComments(Context context, String postID) {
        return RestClient.getService().loadComments(
                new PointRestService.Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).build());
    }

    public boolean createComment(Context context, String post, String text) {
        CommentRequest createRequest = new CommentRequest();
        createRequest.post = post;
        createRequest.app_v = DeviceInformationManager.getAppVersion();
        createRequest.dev_model = DeviceInformationManager.getDeviceModel();
        createRequest.dev_os = "OS: android : " + DeviceInformationManager.getOSVersion() + " : " +
                DeviceInformationManager.getSDKName() + " : sdk=" + DeviceInformationManager
                .getSDKVersion();
        createRequest.user = AuthManager.getAuthToken(context);
        createRequest.text = text;
        Gson gson = new GsonBuilder().setPrettyPrinting()

                .create();
        String json = gson.toJson(createRequest);
        try {

            Reader jsonResponse = this.post(Routes.CREATE_COMMENT, json);
//            Log.e("create response",jsonResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
