package com.easy.pointapp.vcs.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.easy.pointapp.model.api.v1.Authorization;

import java.lang.ref.WeakReference;

/**
 * Created by mini1 on 08.08.15.
 */

public class AuthTask extends AsyncTask<Void, Void, Void> {
    public interface AuthTaskClient
    {
        void authFinished(boolean result);
        Location getCurrentLocation();
    }

    WeakReference<Activity> weakActivity;
    WeakReference<AuthTaskClient> weakClient;
    ProgressDialog dialog;
    boolean result;

    public AuthTask(Activity activity, AuthTaskClient client)
    {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.weakClient = new WeakReference<AuthTaskClient>(client);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(weakActivity.get());
        dialog.setTitle("Authenticating...");
        dialog.setTitle("Please wait...I know, that it's boring, but it is important");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Authorization authorization = new Authorization();
        this.result = authorization.makeAuth(weakActivity.get(),weakClient.get().getCurrentLocation());
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        dialog.dismiss();
        if(!this.result)
        {
            if(weakActivity.get()!=null)
            {
                Toast.makeText(weakActivity.get(), "Error occured(", Toast.LENGTH_LONG);
            }
        }
        else
        {
            if(weakClient.get()!=null)
            {
                weakClient.get().authFinished(this.result);
            }
        }
    }
}