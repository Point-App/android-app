package com.easy.pointapp.vcs;

import com.easy.pointapp.model.api.v1.Post;

import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;

import java.util.List;

/**
 * Created by mini1 on 09.08.15.
 */
public class FavoritesAdapter extends RVAdapter {

    public FavoritesAdapter(List<Post> posts) {
        super(posts);
    }

    public void onBindViewHolder(ConcretePostViewHolder personViewHolder, int i) {
        personViewHolder.commentsTV.setText(posts.get(i).getCommentsNumber() == null ? "0"
                : Integer.toString(posts.get(i).getCommentsNumber()));
        personViewHolder.distanceTV.setText("");
        personViewHolder.likesTV.setText(posts.get(i).getLikesNumber() == null ? "0"
                : Integer.toString(posts.get(i).getLikesNumber()));
        personViewHolder.postID = posts.get(i).getID();
        personViewHolder.rl.setBackgroundColor(Color.parseColor(posts.get(i).getBackdropColor()));

        personViewHolder.postTV.setAutoLinkMask(Linkify.ALL);
        personViewHolder.postTV.setClickable(true);
        personViewHolder.postTV.setMovementMethod(LinkMovementMethod.getInstance());
        personViewHolder.postTV.setText(posts.get(i).getText());
    }
}
