package com.easy.pointapp.model.api.v1;

import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.RestClient;

import android.content.Context;

import java.util.List;

import rx.Observable;

/**
 * Created by mini1 on 03.07.15.
 */
public class CommentsLoader extends RestClient {

    public static Observable<Void> like(Context context, String commentID) {
        return RestClient.getService().sendLike(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("type", "comment").addValue("target", commentID).build());
    }

    public static Observable<List<Comment>> loadComments(Context context, String postID) {
        return RestClient.getService().loadComments(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).build());
    }

    public static Observable<Void> createComment(Context context, String postID, String comment) {
        return RestClient.getService().sendComment(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).addValue("text", comment).build());
    }
}
