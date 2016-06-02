package com.easy.pointapp;

import com.easy.pointapp.model.RestClient;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.views.vholders.CommentViewHolder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nixan on 05.04.16.
 */
public class PostFragment extends Fragment {

    public static final PostFragment newInstance(Post post) {
        PostFragment postFragment = new PostFragment();
        postFragment.mPost = post;
        return postFragment;
    }

    private Post mPost;

    private CommentsAdapter mCommentsAdapter = new CommentsAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.post_fragment, container, false);
        ((Toolbar) result.findViewById(R.id.anim_toolbar)).setTitle(mPost.getText());
        ((RecyclerView) result.findViewById(R.id.scrollableview))
                .setLayoutManager(new LinearLayoutManager(getContext()));
        ((RecyclerView) result.findViewById(R.id.scrollableview)).setAdapter(mCommentsAdapter);
        RestClient.loadComments(getContext(), mPost.getID())
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<List<Comment>>() {
                    @Override
                    public void call(List<Comment> comments) {
                        mCommentsAdapter.setItems(comments);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
        return result;
    }

    private static class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private final List<Comment> mCommentList = new ArrayList<>();

        public void setItems(List<Comment> commentList) {
            mCommentList.clear();
            mCommentList.addAll(commentList);
            notifyDataSetChanged();
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_card_view, parent, false));
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            holder.setComment(mCommentList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCommentList.size();
        }
    }
}
