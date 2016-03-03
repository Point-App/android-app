package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.PostsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */
public class AddPostTask extends AsyncTask<Void, Void, Void> {
    public interface AddPostClient
    {
        Location getCurrentLocation();
        void postAdded(boolean result);
    }
    ProgressDialog dialog;
    boolean result;
    String post;
    WeakReference<Activity> weakActivity;
    WeakReference<AddPostClient> weakClient;
    WeakReference<IAsyncVC> weakVC;
    public AddPostTask(String postText,Activity activity,AddPostClient client, IAsyncVC asyncVC)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<AddPostClient>(client);
        this.post = postText. replaceAll("\n"," ");
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
        PostsLoader loader = new PostsLoader();
        this.result = loader.createPost(this.weakActivity.get(),this.weakClient.get().getCurrentLocation(),this.post);
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
            weakClient.get().postAdded(this.result);
        }
    }
}