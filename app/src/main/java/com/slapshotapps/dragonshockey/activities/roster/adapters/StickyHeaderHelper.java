package com.slapshotapps.dragonshockey.activities.roster.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * A class to help keep a header showing at the top of the RecyclerView
 * while the view is scrolling.  This will work fine for just one header,
 * if multiple headers are needed some updating will be required.
 */
public class StickyHeaderHelper extends RecyclerView.OnScrollListener {

    private RosterAdapter rosterAdapter;
    private RecyclerView recyclerView;
    private View stickyHeaderView;
    private RosterAdapter.HeaderView stickyHeaderViewHolder;
    private int stickyHeaderPosition = RecyclerView.NO_POSITION;

    public StickyHeaderHelper(RosterAdapter adapter){
        rosterAdapter = adapter;
    }

    public void attach(RecyclerView recyclerView){

        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(this);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                initStickyHeader();
            }
        });
    }

    public void detach(){
        recyclerView.removeOnScrollListener(this);

        stickyHeaderViewHolder.setIsRecyclable(true);
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        Log.d("stickyheader", "onscrolled called");
        translateHeader();

        //super.onScrolled(recyclerView, dx, dy);
    }


    private void initStickyHeader(){
        //stickyHeaderView = rosterAdapter.getStickyHeader();

        int newStickyHeaderPosition = getStickyHeaderPosition(RecyclerView.NO_POSITION);

        if(newStickyHeaderPosition != RecyclerView.NO_POSITION){
            updateStickyHeader(newStickyHeaderPosition);
        }


    }


    private int getStickyHeaderPosition(int currentPosition){

        if (currentPosition == RecyclerView.NO_POSITION) {
            View firstChild = recyclerView.getChildAt(0);
            return recyclerView.getChildAdapterPosition(firstChild);
        }

        return RecyclerView.NO_POSITION;

    }

    private void updateStickyHeader(int newPosition){
        if(newPosition!=stickyHeaderPosition){
            stickyHeaderPosition = newPosition;

            stickyHeaderViewHolder = (RosterAdapter.HeaderView)recyclerView.findViewHolderForAdapterPosition(stickyHeaderPosition);

            if(stickyHeaderViewHolder != null){
                stickyHeaderViewHolder.setIsRecyclable(false);
                stickyHeaderView = stickyHeaderViewHolder.getContentView();
                //keep the header on top
                stickyHeaderView.setZ(1f);
                //ensureHeaderParent();
            }
        }
    }

    private void ensureHeaderParent() {
        final View view = stickyHeaderViewHolder.getContentView();
        //Make sure the item params are kept if WRAP_CONTENT has been set for the Header View
        stickyHeaderViewHolder.itemView.getLayoutParams().width = view.getMeasuredWidth();
        stickyHeaderViewHolder.itemView.getLayoutParams().height = view.getMeasuredHeight();
        //Now make sure the params are transferred to the StickyHolderLayout
        ViewGroup.LayoutParams params = stickyHeaderView.getLayoutParams();
        params.width = view.getMeasuredWidth();
        params.height = view.getMeasuredHeight();
        removeViewFromParent(view);
//        stickyHeaderView.setClipToPadding(false);
//        stickyHeaderView.addView(view);
    }

    private void removeViewFromParent(final View view) {
        final ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    private void translateHeader(){
        if(stickyHeaderViewHolder == null){
            Log.d("stickyheader","header is NULL");
            return;
        }

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            final View nextChild = recyclerView.getChildAt(i);

            Log.d("stickyheader","childTop(0) = " + recyclerView.getChildAt(0).getTop());
            if (nextChild.getTop() < 0) {

                int headerHeight = stickyHeaderView.getHeight();
                int headerOffsetY = -1 * nextChild.getTop();// - headerHeight;//Math.min(nextChild.getTop() - headerHeight, 0);

              //  Log.d("stickyheader","header Height = " + headerHeight + ", Offset = " + headerOffsetY);
                if (headerOffsetY > 0){
                    stickyHeaderView.setTranslationY(headerOffsetY);
                    break;
                }
            }
        }

    }

}
