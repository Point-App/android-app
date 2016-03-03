package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.CommentsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */
public class AddCommentTask extends AsyncTask<Void, Void, Void> {
    public interface AddCommentClient
    {
        void commentAdded(boolean result);
    }


    ProgressDialog dialog;
    boolean result;
    String postID;
    String comment;
    WeakReference<Activity> weakActivity;
    WeakReference<IAsyncVC> weakVC;
    WeakReference<AddCommentClient> weakClient;
    public AddCommentTask(String postID, String comment,Activity activity, AddCommentClient client, IAsyncVC vc)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakVC = new WeakReference<IAsyncVC>(vc);
        this.weakClient = new WeakReference<AddCommentClient>(client);
        this.postID = postID;
        this.comment = comment;
    }
    @Override
    protected void onPreExecute() {

        if(weakVC.get()!=null)
        {
            weakVC.get().backgroundWorkStarted();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        CommentsLoader loader = new CommentsLoader();
        this.result = loader.createComment(weakActivity.get(), this.postID, this.comment);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(weakVC.get()!=null)
        {
            weakVC.get().backgroundWorkFinished();
        }
        if(this.weakClient.get()!=null)
        {
            weakClient.get().commentAdded(this.result);
        }
    }
}