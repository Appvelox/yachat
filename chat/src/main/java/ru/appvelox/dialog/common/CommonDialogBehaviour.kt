package ru.appvelox.dialog.common

import ru.appvelox.dialog.DialogView

/**
 * Default realization of DialogView [DialogBehaviour]
 */
class CommonDialogBehaviour : DialogBehaviour {
    override val loadMoreListener: DialogView.LoadMoreListener? = null
    override val onDialogClickListener: DialogView.OnDialogClickListener? = null
    override val onDialogLongClickListener: DialogView.OnDialogLongClickListener? = null
    override val onSwipeLeftListener: DialogView.OnSwipeLeftListener? = null
    override val onSwipeRightListener: DialogView.OnSwipeRightListener? = null
}