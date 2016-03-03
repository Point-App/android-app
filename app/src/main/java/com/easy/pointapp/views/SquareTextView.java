package com.easy.pointapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mini1 on 01.09.15.
 */
public class SquareTextView extends TextView {
    public SquareTextView(Context context)
    {
        super(context);
    }
    public SquareTextView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    public SquareTextView(Context context, AttributeSet attrs, int defStyleAttrs)
    {
        super(context,attrs,defStyleAttrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // set the dimensions
        if (widthWithoutPadding < heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }
}
