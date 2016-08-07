package com.slapshotapps.dragonshockey.activities.roster.adapters;

import android.support.v7.widget.RecyclerView;

/**
 * Created by willmetz on 8/6/16.
 */

public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {

    T onCreateHeaderViewHolder(RecyclerView parent);

    void onBindHeaderViewHolder(T viewHolder, int position);

    boolean hasHeader(int position);
}
