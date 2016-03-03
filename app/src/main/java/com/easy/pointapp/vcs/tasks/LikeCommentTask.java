package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.CommentsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */
public class LikeCommentTask extends AsyncTask<Void, Void, Void> {
    public interface LikeCommentClient
    {
        void commentLiked(boolean result);
    }
    ProgressDialog dialog;
    boolean result;
    Comment comment;
    WeakReference<Activity> weakActivity;
    WeakReference<LikeCommentClient> weakClient;
    WeakReference<IAsyncVC> weakVC;
    public LikeCommentTask(Comment comment, Activity activity, LikeCommentClient client, IAsyncVC asyncVC)
    {
        this.comment = comment;
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<LikeCommentClient>(client);
        this.weakVC = new WeakReference<IAsyncVC>(asyncVC);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(weakVC.get()!=null)
        {
            weakVC.get().backgroundWorkStarted();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        CommentsLoader loader = new CommentsLoader();
        this.result = loader.like(weakActivity.get(),comment);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(weakVC.get()!=null)
        {
            weakVC.get().backgroundWorkFinished();
        }
        if(weakClient.get()!=null)
        {
            weakClient.get().commentLiked(this.result);
        }
    }
}