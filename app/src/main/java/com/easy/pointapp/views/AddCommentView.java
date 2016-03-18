package com.easy.pointapp.views;

import com.easy.pointapp.R;
import com.easy.pointapp.model.RestClient;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.IAsyncVC;

import android.content.Context;
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
public class AddCommentView extends RelativeLayout {

    public Post post;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public AddCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void sendComment() {
        EditText postEdit = (EditText) findViewById(R.id.editPost);
        if (TextUtils.isEmpty(postEdit.getText())) {
            Toast.makeText(getContext(), "Too short", Toast.LENGTH_SHORT).show();
        } else {
            ((IAsyncVC) getContext()).backgroundWorkStarted();
            RestClient.sendComment(getContext(), post.getID(), postEdit.getText().toString())
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            ((IAsyncVC) getContext()).backgroundWorkFinished();
                            commentAdded(true);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ((IAsyncVC) getContext()).backgroundWorkFinished();
                            throwable.printStackTrace();
                            commentAdded(false);
                        }
                    });
        }
    }

    public void commentAdded(boolean result) {
        EditText myEditText = (EditText) findViewById(R.id.editPost);
        if (myEditText != null) {
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }

        if (result) {
            ((SingleScreenContainer) getParent()).commentAdded(post);
        } else {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG).show();
        }
    }

    public void setFocus() {
        EditText postEdit = (EditText) findViewById(R.id.editPost);
        postEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(postEdit, InputMethodManager.SHOW_IMPLICIT);
    }
}