package com.easy.pointapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by mini1 on 30.08.15.
 */
public class SquareByHeight extends RelativeLayout {

    public SquareByHeight(Context context) {
        super(context);
    }

    public SquareByHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareByHeight(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}