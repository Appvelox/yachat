package ru.appvelox.dialog.common

import ru.appvelox.dialog.DialogView

/**
 * Listeners for [DialogView]
 */
interface DialogBehaviour {
    val loadMoreListener: DialogView.LoadMoreListener?
    val onDialogClickListener: DialogView.OnDialogClickListener?
    val onDialogLongClickListener: DialogView.OnDialogLongClickListener?
    val onSwipeLeftListener: DialogView.OnSwipeLeftListener?
    val onSwipeRightListener: DialogView.OnSwipeRightListener?
}