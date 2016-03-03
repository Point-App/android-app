package com.easy.pointapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.IAsyncVC;
import com.easy.pointapp.vcs.PostsActivity;
import com.easy.pointapp.vcs.RecyclerItemClickListener;
import com.easy.pointapp.vcs.tasks.AddPostTask;
import com.easy.pointapp.vcs.tasks.LikePostTask;
import com.easy.pointapp.vcs.tasks.LoadPostsTask;

import java.util.ArrayList;

/**
 * Created by mini1 on 10.08.15.
 */
public class AddPostView extends RelativeLayout implements AddPostTask.AddPostClient {
    String postText;
    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }
    public AddPostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void sendPost()
    {
        EditText postEdit = (EditText)findViewById(R.id.editPost);
        this.postText = postEdit.getText().toString();
        if(this.postText.length()==0)
        {
            Toast.makeText(getContext(),"Too short",Toast.LENGTH_SHORT).show();
        }
        else
        {
            AddPostTask postTask = new AddPostTask(postText,(Activity)getContext(),this, (IAsyncVC)getContext());
            postTask.execute();
        }
    }
    public void postAdded(boolean result)
    {
        EditText myEditText = (EditText) findViewById(R.id.editPost);
        if (myEditText != null) {
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }
        if(result)
        {

            ((SingleScreenContainer)getParent()).postAdded();
        }
        else
        {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG).show();
        }
    }
    public Location getCurrentLocation() {
        return ((PostsActivity)getContext()).getCurrentLocation();
    }
    public void setFocus()
    {
        EditText postEdit = (EditText)findViewById(R.id.editPost);
        postEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(postEdit, InputMethodManager.SHOW_IMPLICIT);
    }
}
