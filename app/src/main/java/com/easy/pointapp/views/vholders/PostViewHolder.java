package com.easy.pointapp.views.vholders;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mini1 on 23.08.15.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

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
        cv = (CardView) itemView.findViewById(R.id.cv);
        distanceTV = (TextView) itemView.findViewById(R.id.commentDistanceAndLikes);
        postTV = (TextView) itemView.findViewById(R.id.commentText);
        commentsTV = (TextView) itemView.findViewById(R.id.commentsTV);
        likesTV = (TextView) itemView.findViewById(R.id.likesTV);
        likesLL = (LinearLayout) itemView.findViewById(R.id.likesLL);
        ll = (LinearLayout) itemView.findViewById(R.id.ll);
        rl = (RelativeLayout) itemView.findViewById(R.id.rl);
    }

    public void setPost(Post post) {
        commentsTV.setText(Integer.toString(post.getCommentsNumber()));
        if (TextUtils.isEmpty(post.getDistanceToPost())) {
            distanceTV.setText("");
        } else {
            distanceTV.setText(post.getDistanceToPost());

        }

        likesTV.setText(Integer.toString(post.getLikesNumber()));

        postTV.setAutoLinkMask(Linkify.ALL);
        postTV.setClickable(true);
        postTV.setMovementMethod(LinkMovementMethod.getInstance());
        postTV.setText(post.getText());

        postID = post.getID();
        rl.setBackgroundColor(Color.parseColor(post.getBackdropColor()));
    }
}