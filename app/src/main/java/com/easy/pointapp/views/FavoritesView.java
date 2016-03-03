package com.easy.pointapp.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.FavoritesAdapter;
import com.easy.pointapp.vcs.IAsyncVC;
import com.easy.pointapp.vcs.RVAdapter;
import com.easy.pointapp.vcs.tasks.LoadFavoritesTask;
import com.easy.pointapp.vcs.tasks.LoadPostsTask;

import java.util.ArrayList;

/**
 * Created by mini1 on 09.08.15.
 */
public class FavoritesView extends PostsView implements LoadFavoritesTask.LoadFavoritesClient {
    public FavoritesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void loadPosts()
    {

        LoadFavoritesTask task = new LoadFavoritesTask((Activity)getContext(),this,(IAsyncVC)getContext());
        task.execute();
    }
    public RVAdapter getAdapter()
    {
        return new FavoritesAdapter(new ArrayList<Post>());
    }
}
