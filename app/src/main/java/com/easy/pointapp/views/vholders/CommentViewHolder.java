package com.easy.pointapp.views.vholders;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;

import com.easy.pointapp.views.SquareByHeight;
import com.easy.pointapp.views.SquareLinearLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mini1 on 23.08.15.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder
{
    public CardView cv;
    public TextView createdAtTV;
    public TextView distanceTV;
    public TextView postTV;
    public TextView likesTV;
    public TextView iconTV;
    public Typeface typeface;
    public CommentViewHolder(View itemView) {
        super(itemView);
        cv = (CardView)itemView.findViewById(R.id.cv);
        createdAtTV = (TextView)itemView.findViewById(R.id.createdAtTV);
        distanceTV = (TextView)itemView.findViewById(R.id.distanceTV);
        postTV = (TextView)itemView.findViewById(R.id.postTV);
        likesTV = (TextView)itemView.findViewById(R.id.likesTV);
        iconTV = (TextView)itemView.findViewById(R.id.iconTV);
    }
    public void setComment(Comment comment)
    {
        PrettyTime p = new PrettyTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try
        {
            Date d = sdf.parse(comment.created);
            createdAtTV.setText(p.format(d));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            createdAtTV.setText(p.format(new Date()));
        }

        distanceTV.setText("");
        likesTV.setText("Likes: " + comment.like);
        postTV.setText(comment.text);
        iconTV.setBackgroundColor(Color.parseColor(comment.icon_color));
        try
        {
            int unicodeStr = Integer.parseInt(comment.icon,16);
            Log.d("","u"+unicodeStr);
            if(typeface!=null)
            {
                iconTV.setTypeface(typeface);
            }
            iconTV.setText(Character.toString((char) unicodeStr));
        }
        catch(Exception e)
        {
            iconTV.setText(comment.icon);
            e.printStackTrace();
        }

    }
}