package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.api.v1.PostsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */
public class LikePostTask extends AsyncTask<Void, Void, Void> {
    public interface LikePostClient
    {
        void postLiked(boolean result);
    }
    ProgressDialog dialog;
    boolean result;
    Post post;
    WeakReference<Activity> weakActivity;
    WeakReference<LikePostClient> weakClient;
    WeakReference<IAsyncVC> weakVC;
    public LikePostTask(Post post,Activity activity,LikePostClient client,IAsyncVC asyncVC)
    {
        this.post = post;
        weakActivity = new WeakReference<Activity>(activity);
        weakClient = new WeakReference<LikePostClient>(client);
        weakVC = new WeakReference<IAsyncVC>(asyncVC);
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
        this.result = loader.like(weakActivity.get(),post);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(weakVC.get()!=null)
        {
            weakVC.get().backgroundWorkFinished();
        }
        LikePostClient client = weakClient.get();
        if(client!=null)
        {
            client.postLiked(this.result);
        }
    }
}
