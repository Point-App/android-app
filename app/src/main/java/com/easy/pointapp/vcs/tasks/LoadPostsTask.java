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
 * Created by mini1 on 08.08.15.
 */
public class LoadPostsTask extends AsyncTask<Void, Void, Void> {
    public interface LoadPostsClient
    {
        Location getCurrentLocation();
        void loadedPosts(List<Post> posts);
    }
    ProgressDialog dialog;
    List<Post> posts;
    WeakReference<Activity> weakActivity;
    WeakReference<LoadPostsClient> weakClient;
    WeakReference<IAsyncVC>weakVC;
    public LoadPostsTask(Activity activity, LoadPostsClient client,IAsyncVC asyncVC)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<LoadPostsClient>(client);
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
        posts = loader.loadPosts(weakActivity.get(),weakClient.get().getCurrentLocation());
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
            weakClient.get().loadedPosts(posts);
        };
    }
}
