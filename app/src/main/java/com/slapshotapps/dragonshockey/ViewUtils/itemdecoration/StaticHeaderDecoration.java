package com.slapshotapps.dragonshockey.ViewUtils.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * An adaption of the https://github.com/edubarr/header-decor to
 * handle just one header rather than a list of headers.
 */
public class StaticHeaderDecoration extends RecyclerView.ItemDecoration {

    private RecyclerView.ViewHolder header;
    private StickyHeaderAdapter adapter;
    private RecyclerView recyclerView;

    public StaticHeaderDecoration(StickyHeaderAdapter adapter, RecyclerView recyclerView) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int layoutPosition = parent.getChildLayoutPosition(view);

        int headerHeight = 0;

        //as this method will determine how much to move the other views for the header we need to
        //see if this is the correct position to offset
        if (layoutPosition != RecyclerView.NO_POSITION && layoutPosition == 0) {
            View header = getHeader().itemView;
            headerHeight = header.getHeight();
        }

        outRect.set(0, headerHeight, 0, 0);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        final int childCount = parent.getChildCount();

        for (int layoutPosition = 0; layoutPosition < childCount; layoutPosition++) {
            View child = parent.getChildAt(layoutPosition);

            final int adapterPosition = parent.getChildAdapterPosition(child);

            if (adapterPosition != RecyclerView.NO_POSITION && layoutPosition == 0) {

                View headerView = getHeader().itemView;
                c.save();
                headerView.draw(c);
                c.restore();
            }
        }
    }

    private RecyclerView.ViewHolder getHeader() {
        if (header == null) {
            header = adapter.onCreateHeaderViewHolder(recyclerView);
            final View headerView = header.itemView;

            adapter.onBindHeaderViewHolder(header);

            //need to take some measurements here as without this the view has no size
            int widthSpec =
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(),
                    View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(),
                    headerView.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(),
                    headerView.getLayoutParams().height);

            headerView.measure(childWidth, childHeight);
            headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
        }

        return header;
    }
}
