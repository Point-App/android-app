package com.easy.pointapp.vcs;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.views.vholders.CommentViewHolder;
import com.easy.pointapp.views.vholders.PostViewHolder;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mini1 on 03.07.15.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> mComments = new ArrayList<>();

    private Post mPost;

    public Typeface typeface;

    public CommentAdapter() {
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    public void setPost(Post post) {
        mPost = post;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPost == null) {
            return 0;
        } else {
            return mComments.size() + 1;
        }


    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.comment_card_view, viewGroup, false);
            CommentViewHolder pvh = new CommentViewHolder(v);

            pvh.typeface = typeface;
            return pvh;
        } else {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.post_card_view, viewGroup, false);
            PostViewHolder pvh = new PostViewHolder(v);
            return pvh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder personViewHolder, int i) {
        if (i == 0) {
            ((PostViewHolder) personViewHolder).setPost(mPost);
        } else {
            ((CommentViewHolder) personViewHolder).setComment(mComments.get(i - 1));
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}