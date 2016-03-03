package com.easy.pointapp.views.vholders;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;

/**
 * Created by mini1 on 23.08.15.
 */
public class PostViewHolder extends RecyclerView.ViewHolder
{
    public String postID;
    public CardView cv;
    public TextView commentsTV;
    public TextView distanceTV;
    public TextView postTV;
    public TextView likesTV;
    public LinearLayout ll;
    public LinearLayout likesLL;
    public RelativeLayout rl;
    public PostViewHolder(View itemView) {
        super(itemView);
        cv = (CardView)itemView.findViewById(R.id.cv);
        distanceTV = (TextView)itemView.findViewById(R.id.distanceTV);
        postTV = (TextView)itemView.findViewById(R.id.postTV);
        commentsTV = (TextView)itemView.findViewById(R.id.commentsTV);
        likesTV = (TextView)itemView.findViewById(R.id.likesTV);
        likesLL = (LinearLayout)itemView.findViewById(R.id.likesLL);
        ll = (LinearLayout)itemView.findViewById(R.id.ll);
        rl = (RelativeLayout)itemView.findViewById(R.id.rl);
    }
    public void setPost(Post post)
    {
        commentsTV.setText(""+post.comments);
        if(post.distance==null)
        {
            distanceTV.setText("");
        }
        else
        {
            distanceTV.setText(post.distance);

        }

        likesTV.setText(""+post.like);
        postTV.setText(post.text);
        postID = post._id;
        rl.setBackgroundColor(Color.parseColor(post.backdrop));
    }
}