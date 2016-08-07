package com.slapshotapps.dragonshockey.activities.roster.views;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.slapshotapps.dragonshockey.activities.roster.adapters.StickyHeaderAdapter;

/**
 * An adapttion of the https://github.com/edubarr/header-decor to
 * handle just one header rather than a list of headers.
 */
public class RosterHeaderDecoration extends RecyclerView.ItemDecoration {

    private RecyclerView.ViewHolder header;
    private StickyHeaderAdapter adapter;
    private RecyclerView recyclerView;

    public RosterHeaderDecoration(StickyHeaderAdapter adapter, RecyclerView recyclerView){
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        int headerHeight = 0;
        if (position != RecyclerView.NO_POSITION && adapter.hasHeader(position)) {
            View header = getHeader(position).itemView;
            headerHeight = header.getHeight();
        }

        outRect.set(0, headerHeight, 0, 0);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        final int childCount = parent.getChildCount();

        for(int layoutPosition = 0; layoutPosition < childCount; layoutPosition++){
            View child = parent.getChildAt(layoutPosition);

            final int adapterPosition = parent.getChildAdapterPosition(child);

            if( adapterPosition != RecyclerView.NO_POSITION &&
                    (layoutPosition == 0 || adapter.hasHeader(adapterPosition))){

                View headerView = getHeader(adapterPosition).itemView;
                c.save();
                final int left = child.getLeft();
                final int top = 0;
                c.translate(left, top);
                headerView.setTranslationX(left);
                headerView.setTranslationY(top);
                headerView.draw(c);
                c.restore();

            }
        }
    }

    private RecyclerView.ViewHolder getHeader(int position){
        if(header==null){
            header = adapter.onCreateHeaderViewHolder(recyclerView);
            final View headerView = header.itemView;

            adapter.onBindHeaderViewHolder(header, position);

            //need to take some measurements here as without this the view has no size
            int widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(), headerView.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(), headerView.getLayoutParams().height);

            headerView.measure(childWidth, childHeight);
            headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
        }

        return header;
    }


}
