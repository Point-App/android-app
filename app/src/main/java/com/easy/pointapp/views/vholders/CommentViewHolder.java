package com.easy.pointapp.views.vholders;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;

import org.ocpsoft.prettytime.PrettyTime;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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

    private CardView mCardView;

    private TextView mCreatedAndLikesText;

    private TextView mCommentText;

    private TextView mIcon;

    private Typeface mTypeface;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mTypeface = Typeface.createFromAsset(itemView.getContext().getAssets(),
                "fonts/webhostinghub-glyphs.ttf");
        mCardView = (CardView) itemView.findViewById(R.id.cv);
        mCreatedAndLikesText = (TextView) itemView.findViewById(R.id.commentDistanceAndLikes);
        mCommentText = (TextView) itemView.findViewById(R.id.commentText);
        mIcon = (TextView) itemView.findViewById(R.id.commentIcon);
    }

    public void setComment(Comment comment) {
        PrettyTime p = new PrettyTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String distanceAndLikes = "";
        try {
            Date d = sdf.parse(comment.getCreatedDate());
            distanceAndLikes = p.format(d) + " – ";
        } catch (Exception e) {
            e.printStackTrace();
        }

        distanceAndLikes = distanceAndLikes + "Likes: " + (comment.getLikeNumber() == 0 ? "0"
                : Integer.toString(comment.getLikeNumber()));

        mCreatedAndLikesText.setText(distanceAndLikes);
        mCommentText.setText(comment.getText());

        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        if (TextUtils.isEmpty(comment.getIconColor()) || !comment.getIconColor()
                .matches("#[A-Fa-f0-9]{6}")) {
            shapeDrawable.getPaint().setColor(Color.BLACK);
        } else {
            shapeDrawable.getPaint().setColor(Color.parseColor(comment.getIconColor()));
        }
        mIcon.setBackground(shapeDrawable);



        try {
            int unicodeStr = Integer.parseInt(comment.getIcon(), 16);
            Log.d("", "u" + unicodeStr);
            if (mTypeface != null) {
                mIcon.setTypeface(mTypeface);
            }
            mIcon.setText(Character.toString((char) unicodeStr));
        } catch (Exception e) {
            mIcon.setText(comment.getIcon());
            e.printStackTrace();
        }

    }
}