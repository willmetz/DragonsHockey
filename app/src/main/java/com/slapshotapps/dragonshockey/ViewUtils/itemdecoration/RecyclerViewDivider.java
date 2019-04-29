package com.slapshotapps.dragonshockey.ViewUtils.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A class to draw a divider in a recycler view.
 * <p>
 * Androids coordinate system
 * <p>
 * (0,0)  -->   X
 * |
 * v
 * <p>
 * Y
 */

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private Drawable dividerDrawable;

    public RecyclerViewDivider(Context context, int drawableID) {
        dividerDrawable = ContextCompat.getDrawable(context, drawableID);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //measure the view (need to leave left padding in here)
        final int right = parent.getWidth() - parent.getPaddingRight();

        //the left edge starts where the view padding ends
        final int left = parent.getPaddingLeft();

        final int drawableHeight = dividerDrawable.getIntrinsicHeight();

        //assume all children have the same width here...
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            final int top = child.getBottom() - drawableHeight;
            final int bottom = child.getBottom();

            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }
}
