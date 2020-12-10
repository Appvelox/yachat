package ru.appvelox.dialog

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeCallback : ItemTouchHelper.Callback() {

    var swipeLeftListener: DialogView.OnSwipeLeftListener? = null
    var swipeRightListener: DialogView.OnSwipeRightListener? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.START) {
            swipeLeftListener?.onAction((viewHolder as DialogViewHolder).dialog)
        } else if (direction == ItemTouchHelper.END) {
            swipeRightListener?.onAction((viewHolder as DialogViewHolder).dialog)
        }
    }
}