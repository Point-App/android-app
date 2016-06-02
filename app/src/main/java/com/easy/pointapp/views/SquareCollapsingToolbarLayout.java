package com.easy.pointapp.views;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by nixan on 03.06.16.
 */

public class SquareCollapsingToolbarLayout extends CollapsingToolbarLayout {

    public SquareCollapsingToolbarLayout(Context context) {
        super(context);
    }

    public SquareCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
