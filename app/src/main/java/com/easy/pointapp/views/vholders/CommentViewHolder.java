package com.easy.pointapp.views.vholders;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;

import org.ocpsoft.prettytime.PrettyTime;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mini1 on 23.08.15.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public CardView cv;

    public TextView createdAtTV;

    public TextView distanceTV;

    public TextView postTV;

    public TextView likesTV;

    public TextView iconTV;

    public Typeface typeface;

    public CommentViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        createdAtTV = (TextView) itemView.findViewById(R.id.createdAtTV);
        distanceTV = (TextView) itemView.findViewById(R.id.distanceTV);
        postTV = (TextView) itemView.findViewById(R.id.postTV);
        likesTV = (TextView) itemView.findViewById(R.id.likesTV);
        iconTV = (TextView) itemView.findViewById(R.id.iconTV);
    }

    public void setComment(Comment comment) {
        PrettyTime p = new PrettyTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date d = sdf.parse(comment.getCreatedDate());
            createdAtTV.setText(p.format(d));
        } catch (Exception e) {
            e.printStackTrace();
            createdAtTV.setText(p.format(new Date()));
        }

        distanceTV.setText("");
        likesTV.setText("Likes: " + (comment.getLikeNumber() == 0 ? "0"
                : Integer.toString(comment.getLikeNumber())));
        postTV.setText(comment.getText());
        if (TextUtils.isEmpty(comment.getIconColor()) || !comment.getIconColor()
                .matches("#[A-Fa-f0-9]{6}")) {
            iconTV.setBackgroundColor(Color.BLACK);
        } else {
            iconTV.setBackgroundColor(Color.parseColor(comment.getIconColor()));
        }
        try {
            int unicodeStr = Integer.parseInt(comment.getIcon(), 16);
            Log.d("", "u" + unicodeStr);
            if (typeface != null) {
                iconTV.setTypeface(typeface);
            }
            iconTV.setText(Character.toString((char) unicodeStr));
        } catch (Exception e) {
            iconTV.setText(comment.getIcon());
            e.printStackTrace();
        }

    }
}