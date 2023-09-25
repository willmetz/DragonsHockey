package com.slapshotapps.dragonshockey.ViewUtils.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewbinding.ViewBinding
import com.slapshotapps.dragonshockey.ViewUtils.interfaces.StickyHeaderAdapter

/**
 * An adaption of the https://github.com/edubarr/header-decor to
 * handle just one header rather than a list of headers.
 */
class StaticHeaderDecoration(private val adapter: StickyHeaderAdapter<*>, private val recyclerView: RecyclerView) : ItemDecoration() {
    private var binding: ViewBinding? = null
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutPosition = parent.getChildLayoutPosition(view)
        var headerHeight = 0

        //as this method will determine how much to move the other views for the header we need to
        //see if this is the correct position to offset
        if (layoutPosition == 0) {
            val header = header
            headerHeight = header.height
        }
        outRect[0, headerHeight, 0] = 0
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (layoutPosition in 0 until childCount) {
            val child = parent.getChildAt(layoutPosition)
            val adapterPosition = parent.getChildAdapterPosition(child)
            if (adapterPosition != RecyclerView.NO_POSITION && layoutPosition == 0) {
                val headerView = header
                c.save()
                headerView.draw(c)
                c.restore()
            }
        }
    }

    private val header: View
        private get() {
            if (binding == null) {
                binding = adapter.onCreateViewBinding(recyclerView)
                val headerView = binding!!.root
                adapter.onBindHeaderViewHolder(binding!!)

                //need to take some measurements here as without this the view has no size
                val widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY)
                val heightSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.height,
                        View.MeasureSpec.UNSPECIFIED)
                val childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                        recyclerView.paddingLeft + recyclerView.paddingRight,
                        headerView.layoutParams.width)
                val childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                        recyclerView.paddingTop + recyclerView.paddingBottom,
                        headerView.layoutParams.height)
                headerView.measure(childWidth, childHeight)
                headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
            }
            return binding!!.root
        }
}