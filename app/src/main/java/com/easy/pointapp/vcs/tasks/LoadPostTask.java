package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.api.v1.PostsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mini1 on 20.09.15.
 */
public class LoadPostTask extends AsyncTask<Void, Void, Void> {
    public interface LoadPostClient
    {
        void loadedPost(Post post);
    }
    Post postToView;
    Post loadedPost;
    WeakReference<Activity> weakActivity;
    WeakReference<LoadPostClient> weakClient;
    WeakReference<IAsyncVC>weakVC;
    public LoadPostTask(Activity activity, LoadPostClient client,IAsyncVC asyncVC, Post postToView)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<LoadPostClient>(client);
        this.weakVC = new WeakReference<IAsyncVC>(asyncVC);
        this.postToView = postToView;
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
        loadedPost = loader.loadSingle(weakActivity.get(),postToView);

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
            weakClient.get().loadedPost(loadedPost);
        };
    }
}
