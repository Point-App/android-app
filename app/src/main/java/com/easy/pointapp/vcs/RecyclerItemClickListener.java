package com.easy.pointapp.vcs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mini1 on 03.07.15.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
{

    public static interface OnItemClickListener
    {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener)
    {
        mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                View likesView = childView.findViewWithTag("LIKES");

                if(childView.isClickable())
                {
                    if(likesView!=null)
                    {
                        likesView.getX();
                        likesView.getY();
                        int[] location = new int[2];
                        likesView.getLocationOnScreen(location);
                        if(location[0]<=e.getRawX()&&e.getRawX()<=(location[0]+likesView.getWidth())&&
                        location[1]<=e.getRawY()&&e.getRawY()<=(location[1]+likesView.getHeight()))
                        return false;

                    };
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if(childView != null && mListener != null)
                {
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
    {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
        {
            mListener.onItemClick(childView, view.getChildPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent){}
}
