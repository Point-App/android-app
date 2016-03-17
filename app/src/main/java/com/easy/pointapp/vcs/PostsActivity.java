package com.easy.pointapp.vcs;

import com.crashlytics.android.Crashlytics;
import com.easy.pointapp.R;
import com.easy.pointapp.model.AuthManager;
import com.easy.pointapp.model.api.v1.AuthenticationHolder;
import com.easy.pointapp.model.api.v1.Authorization;
import com.easy.pointapp.views.Container;
import com.easy.pointapp.views.ContainerClient;
import com.easy.pointapp.views.SingleScreenContainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import info.hoang8f.android.segmented.SegmentedGroup;
import io.fabric.sdk.android.Fabric;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PostsActivity extends AbstractLocationActivity implements ContainerClient, IAsyncVC {

    boolean noLocationFlag;

    Container container;

    ImageButton itemSendPost;

    ImageButton itemAddPost;

    ImageButton itemSync;

    ImageButton itemBack;

    RadioButton btn;

    RadioButton btnFavorites;

    SegmentedGroup tabsGroup;

    TextView actionBarTitle;

    Integer scrollPosition = -1;

    public void saveScrollPosition(Integer newScrollPosition) {
        this.scrollPosition = newScrollPosition;
    }

    public Integer getScrollPosition() {
        return scrollPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_posts);
        noLocationFlag = false;
        container = (Container) findViewById(R.id.singleScreenContainer);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        bar.setDisplayShowTitleEnabled(false);
        bar.setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.action_bar_background)));

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.FILL_PARENT);
        View itemTabBar = inflator.inflate(R.layout.main_action_bar, null);
        tabsGroup = (SegmentedGroup) itemTabBar.findViewById(R.id.segmentedGroup);
        itemBack = (ImageButton) itemTabBar.findViewById(R.id.btn_back);
        itemSync = (ImageButton) itemTabBar.findViewById(R.id.action_sync);
        itemSendPost = (ImageButton) itemTabBar.findViewById(R.id.action_send);
        itemSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
        itemAddPost = (ImageButton) itemTabBar.findViewById(R.id.action_add_post);
        itemAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
        itemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.onBackPressed();
            }
        });
        actionBarTitle = (TextView) itemTabBar.findViewById(R.id.action_bar_title);
        btn = (RadioButton) tabsGroup.findViewById(R.id.postsBtn);
        btn.setChecked(true);
        bar.setCustomView(itemTabBar, lp);
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    container.showPosts();
                }
            }
        });
        btnFavorites = (RadioButton) tabsGroup.findViewById(R.id.favoritesBtn);
        btnFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    container.showFavorites();
                }
            }
        });


    }

    public Container getContainer() {
        return container;
    }

    @Override
    public void onBackPressed() {
        boolean handled = container.onBackPressed();
        if (!handled) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.mCurrentLocation == null) {
            noLocationFlag = true;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
        itemSendPost.setVisibility(View.INVISIBLE);
        itemBack.setVisibility(View.VISIBLE);
        if (itemSync != null) {
            Animation rotationAnimation = AnimationUtils
                    .loadAnimation(getApplicationContext(), R.anim.rotationanimation);
            itemSync.startAnimation(rotationAnimation);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        noLocationFlag = false;
        if (!noLocationFlag && AuthManager.getAuthToken(this) == null) {
            this.makeAuth();
        } else if (!noLocationFlag) {
            container.showPosts();
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void addPost() {
        itemSendPost.setVisibility(View.VISIBLE);
        tabsGroup.setVisibility(View.INVISIBLE);
        itemBack.setVisibility(View.VISIBLE);
        itemAddPost.setVisibility(View.INVISIBLE);
        container.showCreatePost();
    }

    public void sendPost() {
        container.sendPost();
    }

    public void makeAuth() {
        Authorization.makeAuth(this, getCurrentLocation()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AuthenticationHolder>() {
                    @Override
                    public void call(AuthenticationHolder authenticationHolder) {
                        AuthManager
                                .setAuthToken(PostsActivity.this, authenticationHolder.getToken());
                        authFinished(true);
                    }
                });
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public void authFinished(boolean result) {
        if (result) {
            container.authSuccessful();
            //loadPosts();
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void updateActionBar(int screenID) {
        switch (screenID) {
            case SingleScreenContainer.ADD_POST_SCREEN:
                itemSendPost.setVisibility(View.VISIBLE);
                tabsGroup.setVisibility(View.INVISIBLE);
                itemAddPost.setVisibility(View.INVISIBLE);
                itemBack.setVisibility(View.VISIBLE);
                actionBarTitle.setVisibility(View.VISIBLE);
                itemSync.setVisibility(View.INVISIBLE);
                break;
            case SingleScreenContainer.FAVORITES_SCREEN:
                itemSendPost.setVisibility(View.INVISIBLE);
                tabsGroup.setVisibility(View.VISIBLE);
                itemAddPost.setVisibility(View.VISIBLE);
                btn.setChecked(false);
                btnFavorites.setChecked(true);
                itemBack.setVisibility(View.INVISIBLE);
                actionBarTitle.setVisibility(View.INVISIBLE);
                itemSync.setVisibility(View.INVISIBLE);
                break;
            case SingleScreenContainer.POSTS_SCREEN:
                itemSendPost.setVisibility(View.INVISIBLE);
                tabsGroup.setVisibility(View.VISIBLE);
                itemAddPost.setVisibility(View.VISIBLE);
                btn.setChecked(true);
                btnFavorites.setChecked(false);
                itemBack.setVisibility(View.INVISIBLE);
                actionBarTitle.setVisibility(View.INVISIBLE);
                itemSync.setVisibility(View.INVISIBLE);
                break;
            case SingleScreenContainer.ADD_COMMENT_SCREEN:
                itemSendPost.setVisibility(View.VISIBLE);
                ;
                tabsGroup.setVisibility(View.INVISIBLE);
                itemAddPost.setVisibility(View.INVISIBLE);
                actionBarTitle.setVisibility(View.INVISIBLE);
                itemSync.setVisibility(View.INVISIBLE);
                break;
            case SingleScreenContainer.COMMENTS_SCREEN:
                itemSendPost.setVisibility(View.INVISIBLE);
                tabsGroup.setVisibility(View.INVISIBLE);
                itemAddPost.setVisibility(View.INVISIBLE);
                itemBack.setVisibility(View.VISIBLE);
                actionBarTitle.setVisibility(View.INVISIBLE);
                itemSync.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void backgroundWorkStarted() {
        itemSync.setVisibility(View.VISIBLE);
        if (itemSync != null) {
            Animation rotationAnimation = AnimationUtils
                    .loadAnimation(getApplicationContext(), R.anim.rotationanimation);
            itemSync.startAnimation(rotationAnimation);
        }
    }

    public void backgroundWorkFinished() {
        itemSync.setVisibility(View.INVISIBLE);
        if (itemSync != null) {
            itemSync.clearAnimation();
        }
    }
}
