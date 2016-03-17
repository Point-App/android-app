package com.easy.pointapp.views;

import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.api.v1.PostsLoader;
import com.easy.pointapp.vcs.FavoritesAdapter;
import com.easy.pointapp.vcs.RVAdapter;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mini1 on 09.08.15.
 */
public class FavoritesView extends PostsView {

    public FavoritesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadPosts() {
        PostsLoader.loadChosenPosts(getContext()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Post>>() {
            @Override
            public void call(List<Post> posts) {
                loadedPosts(posts);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                failedLoad();
            }
        });
    }

    public RVAdapter getAdapter() {
        return new FavoritesAdapter(new ArrayList<Post>());
    }
}
