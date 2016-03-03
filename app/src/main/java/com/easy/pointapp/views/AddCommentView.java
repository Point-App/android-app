package com.easy.pointapp.views;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.IAsyncVC;
import com.easy.pointapp.vcs.PostsActivity;
import com.easy.pointapp.vcs.tasks.AddCommentTask;
import com.easy.pointapp.vcs.tasks.AddPostTask;

/**
 * Created by mini1 on 10.08.15.
 */
public class AddCommentView extends RelativeLayout implements AddCommentTask.AddCommentClient {
    String postText;
    public Post post;
    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }
    public AddCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void sendComment()
    {
        EditText postEdit = (EditText)findViewById(R.id.editPost);
        this.postText = postEdit.getText().toString();
        if(this.postText.length()==0)
        {
            Toast.makeText(getContext(), "Too short", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AddCommentTask addCommentTask = new AddCommentTask(post._id,postText,(Activity)getContext(),this,(IAsyncVC)getContext());
            addCommentTask.execute();
        }
    }
    public void commentAdded(boolean result)
    {
        EditText myEditText = (EditText) findViewById(R.id.editPost);
        if (myEditText != null) {
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }

        if(result)
        {
            ((SingleScreenContainer)getParent()).commentAdded(post);
        }
        else
        {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG).show();
        }
    }
    public void setFocus()
    {
        EditText postEdit = (EditText)findViewById(R.id.editPost);
        postEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(postEdit, InputMethodManager.SHOW_IMPLICIT);
    }
}