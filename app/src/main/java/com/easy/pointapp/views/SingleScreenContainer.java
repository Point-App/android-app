package com.easy.pointapp.views;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;


public class SingleScreenContainer extends FrameLayout implements Container {

    public final static int POSTS_SCREEN = 1;

    public final static int FAVORITES_SCREEN = 2;

    public final static int ADD_POST_SCREEN = 3;

    public final static int ADD_COMMENT_SCREEN = 4;

    public final static int COMMENTS_SCREEN = 5;

    private int screenID;

    public SingleScreenContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();


    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = ((Activity) getContext()).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) ((Activity) getContext())
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean onBackPressed() {
        if (this.screenID != POSTS_SCREEN) {
            hideKeyboard();

            if (screenID == ADD_COMMENT_SCREEN) {
                if (this.getChildCount() > 0) {
                    AddCommentView postsView = (AddCommentView) getChildAt(0);
                    removeViewAt(0);
                    showComments(postsView.post);
                }
            } else {
                if (this.getChildCount() > 0) {
                    removeViewAt(0);
                }
                showPosts();
            }
            return true;
        }
        return false;
    }

    @Override
    public void showPosts() {
        if (this.getChildCount() != 0) {
            removeViewAt(0);

        }
        View.inflate(getContext(), R.layout.posts_view, this);
        PostsView postsView = (PostsView) getChildAt(0);
        screenID = POSTS_SCREEN;
        postsView.loadPosts();
        ((ContainerClient) getContext()).updateActionBar(screenID);
    }

    @Override
    public void showComments(Post post) {
        if (this.getChildCount() != 0) {
            removeViewAt(0);

        }
        View.inflate(getContext(), R.layout.comments_view, this);
        CommentsView commentsView = (CommentsView) getChildAt(0);
        screenID = COMMENTS_SCREEN;
        commentsView.setPost(post);
        commentsView.loadComments();
        ((ContainerClient) getContext()).updateActionBar(screenID);
    }

    @Override
    public void showCreateComment(Post post) {
        if (this.getChildCount() != 0) {
            removeViewAt(0);

        }
        View.inflate(getContext(), R.layout.add_comment_view, this);
        AddCommentView addCommentView = (AddCommentView) getChildAt(0);
        screenID = ADD_COMMENT_SCREEN;
        addCommentView.post = post;
        ((ContainerClient) getContext()).updateActionBar(screenID);
        addCommentView.setFocus();
    }

    @Override
    public void showCreatePost() {
        if (screenID == POSTS_SCREEN || screenID == FAVORITES_SCREEN) {
            if (this.getChildCount() != 0) {
                removeViewAt(0);

            }
            View.inflate(getContext(), R.layout.add_post_view, this);
            AddPostView postView = (AddPostView) getChildAt(0);
            screenID = ADD_POST_SCREEN;
            ((ContainerClient) getContext()).updateActionBar(screenID);
            postView.setFocus();
        } else {
            CommentsView addCommentView = (CommentsView) getChildAt(0);
            showCreateComment(addCommentView.getPost());
        }

    }

    @Override
    public void showFavorites() {
        if (this.getChildCount() != 0) {
            removeViewAt(0);

        }
        View.inflate(getContext(), R.layout.favorites_view, this);
        FavoritesView favoritesView = (FavoritesView) getChildAt(0);
        screenID = FAVORITES_SCREEN;
        favoritesView.loadPosts();
        ((ContainerClient) getContext()).updateActionBar(screenID);
    }

    public void sendPost() {
        if (screenID == ADD_POST_SCREEN) {
            AddPostView addPostView = (AddPostView) getChildAt(0);
            addPostView.sendPost();
        } else {
            AddCommentView addCommentView = (AddCommentView) getChildAt(0);
            addCommentView.sendComment();
        }

    }

    public void postAdded() {
        showPosts();
    }

    public void commentAdded(Post post) {
        showComments(post);
    }

    public void authSuccessful() {
        showPosts();
    }
}