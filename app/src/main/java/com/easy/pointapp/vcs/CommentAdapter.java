package com.easy.pointapp.vcs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.views.vholders.CommentViewHolder;
import com.easy.pointapp.views.vholders.PostViewHolder;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by mini1 on 03.07.15.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Comment> posts;
    Post post;
    public Typeface typeface;
    public void refreshPost(Post post)
    {
        if(post!=null)
        {
            this.post = post;
            notifyDataSetChanged();
        }
    }
    public CommentAdapter(List<Comment> posts, Post post){
        this.posts = posts;
        this.post = post;
    }
    public void changeComments(List<Comment> posts, Post post)
    {
        this.posts = posts;
        this.post = post;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(post!=null)
        {
            return posts.size()+1;
        }
        else
        {
            return 0;
        }


    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position==0) return 0;
        return 1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType==1)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_card_view, viewGroup, false);
            CommentViewHolder pvh = new CommentViewHolder(v);

            pvh.typeface = typeface;
            return pvh;
        }
        else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_card_view, viewGroup, false);
            PostViewHolder pvh = new PostViewHolder(v);
            return pvh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder personViewHolder, int i) {
        if(i==0)
        {
            ((PostViewHolder)personViewHolder).setPost(post);
        }
        else
        {
            ((CommentViewHolder)personViewHolder).setComment(posts.get(i - 1));
        }

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}