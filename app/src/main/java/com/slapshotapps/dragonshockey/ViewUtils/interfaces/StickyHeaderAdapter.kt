package com.slapshotapps.dragonshockey.ViewUtils.interfaces

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by willmetz on 8/6/16.
 */
interface StickyHeaderAdapter<T : ViewBinding> {
    fun onCreateViewBinding(parent: RecyclerView): T
    fun onBindHeaderViewHolder(binding: ViewBinding): Unit
}