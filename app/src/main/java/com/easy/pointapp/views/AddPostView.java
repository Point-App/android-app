package com.easy.pointapp.views;

import com.easy.pointapp.R;
import com.easy.pointapp.model.RestClient;
import com.easy.pointapp.vcs.PostsActivity;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mini1 on 10.08.15.
 */
public class AddPostView extends RelativeLayout {

    public AddPostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void sendPost() {
        EditText postEdit = (EditText) findViewById(R.id.editPost);
        if (TextUtils.isEmpty(postEdit.getText())) {
            Toast.makeText(getContext(), "Too short", Toast.LENGTH_SHORT).show();
        } else {
            RestClient.sendPost(getContext(), getCurrentLocation(), postEdit.getText().toString())
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            postAdded(true);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            postAdded(false);
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    public void postAdded(boolean result) {
        EditText myEditText = (EditText) findViewById(R.id.editPost);
        if (myEditText != null) {
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }
        if (result) {

            ((SingleScreenContainer) getParent()).postAdded();
        } else {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG).show();
        }
    }

    public Location getCurrentLocation() {
        return ((PostsActivity) getContext()).getCurrentLocation();
    }

    public void setFocus() {
        EditText postEdit = (EditText) findViewById(R.id.editPost);
        postEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(postEdit, InputMethodManager.SHOW_IMPLICIT);
    }
}
