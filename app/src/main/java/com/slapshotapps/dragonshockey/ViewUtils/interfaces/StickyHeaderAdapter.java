package com.slapshotapps.dragonshockey.ViewUtils.interfaces;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by willmetz on 8/6/16.
 */

public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {

    T onCreateHeaderViewHolder(RecyclerView parent);

    void onBindHeaderViewHolder(T viewHolder);
}
