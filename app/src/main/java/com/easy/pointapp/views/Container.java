package com.easy.pointapp.views;

import com.easy.pointapp.model.api.v1.Post;

/**
 * Created by mini1 on 09.08.15.
 */
public interface Container {
    void showPosts();
    boolean onBackPressed();
    void authSuccessful();
    void showFavorites();
    void showCreatePost();
    void sendPost();
    void showComments(Post post);
    void showCreateComment(Post post);

}
