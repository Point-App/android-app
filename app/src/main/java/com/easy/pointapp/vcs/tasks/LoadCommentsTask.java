package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.CommentsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mini1 on 08.08.15.
 */
public class LoadCommentsTask extends AsyncTask<Void, Void, Void> {
    public interface LoadCommentsClient
    {
        void loadedComments(List<Comment> comments);
    }
    ProgressDialog dialog;
    boolean result;
    String postID;
    WeakReference<Activity> weakActivity;
    WeakReference<LoadCommentsClient> weakClient;
    WeakReference<IAsyncVC> weakVC;
    List<Comment> comments;
    public LoadCommentsTask(String postID,Activity activity,LoadCommentsClient client,IAsyncVC weakVC)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<LoadCommentsClient>(client);
        this.postID = postID;
        this.weakVC = new WeakReference<IAsyncVC>(weakVC);
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
        comments = loader.loadComments(weakActivity.get(), postID);
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
            weakClient.get().loadedComments(comments);
        }
    }
}