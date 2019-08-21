package com.example.movieappstarter.utils.base

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


open class TouchHelperCallback constructor(private val touchHelperAdapter: TouchHelperAdapter) : ItemTouchHelper.Callback() {


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            val swipeFlags = 0
            makeMovementFlags(dragFlags, swipeFlags)
        } else {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }

        touchHelperAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //pass
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = 1.0f

        if (viewHolder is ItemTouchBackgroundViewHolderHelper) {
            // Tell the view holder it's time to restore the idle state
            val itemViewHolder = viewHolder as ItemTouchBackgroundViewHolderHelper
            itemViewHolder.onItemClear()
        }
    }


}