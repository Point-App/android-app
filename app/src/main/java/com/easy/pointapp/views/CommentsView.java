package com.easy.pointapp.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.CommentAdapter;
import com.easy.pointapp.vcs.IAsyncVC;
import com.easy.pointapp.vcs.RVAdapter;
import com.easy.pointapp.vcs.RecyclerItemClickListener;
import com.easy.pointapp.vcs.tasks.AddCommentTask;
import com.easy.pointapp.vcs.tasks.LikeCommentTask;
import com.easy.pointapp.vcs.tasks.LikePostTask;
import com.easy.pointapp.vcs.tasks.LoadCommentsTask;
import com.easy.pointapp.vcs.tasks.LoadPostTask;
import com.easy.pointapp.vcs.tasks.LoadPostsTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mini1 on 10.08.15.
 */
public class CommentsView extends RelativeLayout implements LoadCommentsTask.LoadCommentsClient,LikeCommentTask.LikeCommentClient, AddCommentTask.AddCommentClient, LoadPostTask.LoadPostClient, LikePostTask.LikePostClient {
    Post post;
    List<Comment> comments;
    CommentAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String commentText;
    ImageButton btnAdd;
    Typeface typeface;
    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try{
            typeface = Typeface.createFromAsset(context.getAssets(),"fonts/webhostinghub-glyphs.ttf");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        btnAdd = (ImageButton)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        RecyclerView rv = (RecyclerView)findViewById(R.id.itemsRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        comments = new ArrayList<Comment>();
        adapter = new CommentAdapter(new ArrayList<Comment>(),post);
        adapter.typeface = typeface;
        rv.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadComments();
            }
        });
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                //like comment
                if(position>0)
                {
                    Comment comment = comments.get(position-1);
                    likeComment(comment);
                }
                else
                {
                    likePost();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
    }
    private void likePost()
    {
        LikePostTask task = new LikePostTask(post,(Activity)getContext(),this,(IAsyncVC)getContext());
        task.execute();
    }
    public void sendComment()
    {
        EditText postEdit = (EditText)findViewById(R.id.editComment);
        this.commentText = postEdit.getText().toString();
        if(this.commentText.length()==0)
        {
            Toast.makeText(getContext(), "Too short", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AddCommentTask addCommentTask = new AddCommentTask(post._id,commentText,(Activity)getContext(),this,(IAsyncVC)getContext());
            addCommentTask.execute();
        }
    }
    public void postLiked(boolean result)
    {
        this.refreshPost();
    }
    public void commentAdded(boolean result)
    {
        EditText myEditText = (EditText) findViewById(R.id.editComment);
        if (myEditText != null) {
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }

        if(result)
        {
            loadComments();
            myEditText.setText("");
            this.commentText = "";
        }
        else
        {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG).show();
        }
        refreshPost();
    }
    private void refreshPost()
    {
        if(post!=null)
        {
            LoadPostTask task = new LoadPostTask(((Activity)getContext()),this,(IAsyncVC)getContext(),post);
            task.execute();
        }
    }
    public void loadedPost(Post post)
    {
        if(post!=null)
        {
            this.post = post;
            adapter.refreshPost(this.post);
        }
    }
    private void likeComment(Comment comment)
    {
        LikeCommentTask task = new LikeCommentTask(comment,((Activity)getContext()),this,(IAsyncVC)getContext());
        task.execute();
        refreshPost();
    }
    public void loadComments()
    {
        LoadCommentsTask lct = new LoadCommentsTask(post._id,(Activity)getContext(),this,(IAsyncVC)getContext());
        lct.execute();

    }
    public void loadedComments(List<Comment> comments)
    {
        this.comments = comments;
        if(mSwipeRefreshLayout.isRefreshing())
        {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(comments!=null)
        {
            adapter.changeComments(comments,post);

        }
        else {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG);
        }
        refreshPost();
    }
    public void commentLiked(boolean result)
    {
        loadComments();
    }
}
