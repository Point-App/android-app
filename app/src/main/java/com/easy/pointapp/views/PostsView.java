package com.easy.pointapp.views;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.pointapp.R;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.vcs.IAsyncVC;
import com.easy.pointapp.vcs.PostsActivity;
import com.easy.pointapp.vcs.RVAdapter;
import com.easy.pointapp.vcs.RecyclerItemClickListener;
import com.easy.pointapp.vcs.tasks.LikePostTask;
import com.easy.pointapp.vcs.tasks.LoadPostsTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mini1 on 09.08.15.
 */
public class PostsView extends RelativeLayout implements LoadPostsTask.LoadPostsClient, LikePostTask.LikePostClient {
    List<Post> posts;
    RVAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView stateText;
    RecyclerView rv;
    LinearLayoutManager llm;
    public PostsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getScrollPosition()
    {
        LinearLayoutManager manager = (LinearLayoutManager) rv.getLayoutManager();
        int firstItem = manager.findFirstVisibleItemPosition();
        return firstItem;
    }
    public void setScrollPosition(int firstItem)
    {
        if(firstItem!=-1)
        {
            rv.scrollToPosition(firstItem);
        }
    }
    @Override protected void onDetachedFromWindow(){
        int firstItem = getScrollPosition();
        ((PostsActivity)getContext()).saveScrollPosition(firstItem);
        super.onDetachedFromWindow();

    }
    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        rv = (RecyclerView)findViewById(R.id.itemsRecyclerView);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        posts = new ArrayList<Post>();
        adapter = getAdapter();
        rv.setAdapter(adapter);
        stateText = (TextView)findViewById(R.id.stateTV);
        stateText.setVisibility(TextView.INVISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadPosts();
            }
        });
        adapter.likesListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post._id = adapter.likedID;
                likePost(post);
            }
        };
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Post post = posts.get(position);
                ((SingleScreenContainer) getParent()).showComments(post);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

    }

    private void likePost(Post post)
    {
        LikePostTask task = new LikePostTask(post,((Activity)getContext()),this,(IAsyncVC)getContext());
        task.execute();
    }
    public RVAdapter getAdapter()
    {
        return new RVAdapter(new ArrayList<Post>());
    }
    public void loadPosts()
    {

        LoadPostsTask task = new LoadPostsTask((Activity)getContext(),this,(IAsyncVC)getContext());
        task.execute();
    }
    public void loadedPosts(List<Post> posts)
    {
        this.posts = posts;
        if(mSwipeRefreshLayout.isRefreshing())
        {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(posts!=null)
        {


            adapter.changePosts(posts);

            if(posts.size()==0)
            {
                stateText.setText("Nobody cares");
                stateText.setVisibility(TextView.VISIBLE);
            }
            else
            {
                int scrollPosition = ((PostsActivity)getContext()).getScrollPosition();
                this.setScrollPosition(scrollPosition+1);
                ((PostsActivity)getContext()).saveScrollPosition(-1);

                stateText.setVisibility(TextView.INVISIBLE);


            }

        }
        else {
            stateText.setText("Jesus!\nCouldn't connect to server.\nTry again later.");
            stateText.setVisibility(TextView.VISIBLE);
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG);
        }
    };
    //LikePostClient
    public void postLiked(boolean result)
    {
        if(result)
        {
            loadPosts();
        }
        else {
            Toast.makeText(getContext(), "Error occured(", Toast.LENGTH_LONG);
        }
    }
    public Location getCurrentLocation() {
        return ((PostsActivity)getContext()).getCurrentLocation();
    }
}
