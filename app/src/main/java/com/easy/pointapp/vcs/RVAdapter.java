package com.easy.pointapp.vcs;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;

import java.util.List;

/**
 * Created by mini1 on 03.07.15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ConcretePostViewHolder>{
    public static View.OnClickListener likesListener;
    public static String likedID = "";
    public static class ConcretePostViewHolder extends com.easy.pointapp.views.vholders.PostViewHolder
    {
        ConcretePostViewHolder(View itemView) {
            super(itemView);
            likesLL.setTag("LIKES");
            likesLL.setClickable(true);
            likesLL.setFocusable(true);
            likesLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(likesListener!=null)
                    {
                        likedID = postID;
                        likesListener.onClick(v);
                    }

                }
            });
        }

    }
    List<Post> posts;
    public RVAdapter(List<Post> posts){
        this.posts = posts;
    }
    public void changePosts(List<Post> posts)
    {
        this.posts = posts;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }
    @Override
    public ConcretePostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_card_view, viewGroup, false);
        ConcretePostViewHolder pvh = new ConcretePostViewHolder(v);
        v.setClickable(true);
        return pvh;
    }
    @Override
    public void onBindViewHolder(ConcretePostViewHolder personViewHolder, int i) {
        personViewHolder.commentsTV.setText(""+posts.get(i).comments);
        personViewHolder.distanceTV.setText(posts.get(i).distance);
        personViewHolder.likesTV.setText(""+posts.get(i).like);
        personViewHolder.postTV.setText(posts.get(i).text);
        personViewHolder.postID = posts.get(i)._id;
        personViewHolder.rl.setBackgroundColor(Color.parseColor(posts.get(i).backdrop));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}