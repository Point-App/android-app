package com.easy.pointapp;

import com.easy.pointapp.model.RestClient;
import com.easy.pointapp.model.api.v1.Post;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by nixan on 04.04.16.
 */
public class PostsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final PostsAdapter mAdapter = new PostsAdapter();

    public static final PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_list, container, false);
        ((SwipeRefreshLayout) view.findViewById(R.id.swipe)).setOnRefreshListener(this);
        ((RecyclerView) view.findViewById(R.id.list))
                .setLayoutManager(new LinearLayoutManager(container.getContext()));
        ((RecyclerView) view.findViewById(R.id.list)).setAdapter(mAdapter);
        return view;
    }

    public void loadPosts() {
        new ReactiveLocationProvider(getActivity().getApplicationContext()).getLastKnownLocation()
//                .getUpdatedLocation(
//                LocationRequest.create().setInterval(1000).setSmallestDisplacement(500)
//                        .setFastestInterval(1000)
//                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY))
                .flatMap(new Func1<Location, Observable<List<Post>>>() {
                    @Override
                    public Observable<List<Post>> call(Location location) {
                        return RestClient.loadPosts(getContext(), location);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<List<Post>>() {
                    @Override
                    public void call(List<Post> posts) {
                        mAdapter.setPosts(posts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadPosts();
    }
}