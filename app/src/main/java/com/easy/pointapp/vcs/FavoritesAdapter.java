package com.easy.pointapp.vcs;

import android.graphics.Color;

import com.easy.pointapp.model.api.v1.Post;

import java.util.List;

/**
 * Created by mini1 on 09.08.15.
 */
public class FavoritesAdapter extends RVAdapter {

    public FavoritesAdapter(List<Post> posts){
        super(posts);
    }
    public void onBindViewHolder(ConcretePostViewHolder personViewHolder, int i) {
        personViewHolder.commentsTV.setText(""+posts.get(i).comments);
        personViewHolder.distanceTV.setText("");
        personViewHolder.likesTV.setText(""+posts.get(i).like);
        personViewHolder.postTV.setText(posts.get(i).text);
        personViewHolder.postID = posts.get(i)._id;
        personViewHolder.rl.setBackgroundColor(Color.parseColor(posts.get(i).backdrop));
    }
}
