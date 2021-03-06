package com.easy.pointapp;

import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.views.vholders.PostViewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nixan on 04.04.16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder>
        implements View.OnClickListener {

    private List<Post> mPostList = new ArrayList<>();

    private View.OnClickListener mOnClickListener;

    public void setPosts(List<Post> posts) {
        mPostList.clear();
        mPostList.addAll(posts);
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_card_view, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        view.setClickable(true);
        view.setOnClickListener(this);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.commentsTV
                .setText(Integer.toString(getItemAtPosition(position).getCommentsNumber()));
        holder.distanceTV.setText(getItemAtPosition(position).getDistanceToPost());
        holder.likesTV.setText(Integer.toString(getItemAtPosition(position).getLikesNumber()));
        holder.postID = getItemAtPosition(position).getID();
        holder.cv.setCardBackgroundColor(
                Color.parseColor(getItemAtPosition(position).getBackdropColor()));

        holder.postTV.setAutoLinkMask(Linkify.ALL);
        holder.postTV.setClickable(true);
        holder.postTV.setMovementMethod(LinkMovementMethod.getInstance());
        holder.postTV.setText(getItemAtPosition(position).getText());
    }

    public Post getItemAtPosition(int position) {
        return mPostList.get(position);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
        }
    }

    public List<Post> getPosts() {
        return mPostList;
    }
}
