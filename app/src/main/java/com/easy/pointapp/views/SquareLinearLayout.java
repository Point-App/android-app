package com.easy.pointapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Igor on 01.08.2015.
 */
public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout(Context context)
    {
        super(context);
    }
    public SquareLinearLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyleAttrs)
    {
        super(context,attrs,defStyleAttrs);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
