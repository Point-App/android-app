package com.easy.pointapp.vcs.tasks;

import com.easy.pointapp.model.api.v1.PostsLoader;
import com.easy.pointapp.vcs.IAsyncVC;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */
public class LikePostTask extends AsyncTask<Void, Void, Void> {

    public interface LikePostClient {

        void postLiked(boolean result);
    }

    ProgressDialog dialog;

    boolean result;

    String postID;

    WeakReference<Activity> weakActivity;

    WeakReference<LikePostClient> weakClient;

    WeakReference<IAsyncVC> weakVC;

    public LikePostTask(String postID, Activity activity, LikePostClient client, IAsyncVC asyncVC) {
        this.postID = postID;
        weakActivity = new WeakReference<Activity>(activity);
        weakClient = new WeakReference<LikePostClient>(client);
        weakVC = new WeakReference<IAsyncVC>(asyncVC);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (weakVC.get() != null) {
            weakVC.get().backgroundWorkStarted();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        PostsLoader loader = new PostsLoader();
        this.result = loader.like(weakActivity.get(), postID);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (weakVC.get() != null) {
            weakVC.get().backgroundWorkFinished();
        }
        LikePostClient client = weakClient.get();
        if (client != null) {
            client.postLiked(this.result);
        }
    }
}
