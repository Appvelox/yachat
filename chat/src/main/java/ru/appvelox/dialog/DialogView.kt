package ru.appvelox.dialog

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.appvelox.dialog.common.CommonDialogAppearance
import ru.appvelox.dialog.common.CommonDialogBehaviour
import ru.appvelox.dialog.common.DialogAdapter

/**
 * Component for displaying dialogs
 */
class DialogView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    private val adapter = DialogAdapter(CommonDialogAppearance(context), CommonDialogBehaviour())

    init {
        super.setAdapter(adapter)

        val layoutManager = LinearLayoutManager(context)
        super.setLayoutManager(layoutManager)

        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findFirstVisibleItemPosition() <= 5 && !adapter.oldDataLoading) {
                    adapter.requestPreviousMessagesFromListener()
                }
            }
        })
    }

    fun addDialog(dialog: Dialog) {
        adapter.addDialog(dialog)
        layoutManager?.scrollToPosition(adapter.getLastDialogIndex())
    }

    fun deleteDialog(dialog: Dialog) = adapter.deleteDialog(dialog)

    fun updateDialog(dialog: Dialog) = adapter.updateDialog(dialog)

    fun addDialogs(dialogs: List<Dialog>) = adapter.addDialogs(dialogs)

    fun addOldDialogs(dialogs: List<Dialog>) = adapter.addOldDialogs(dialogs)

    fun setOnItemClickListener(listener: OnDialogClickListener?) {
        adapter.onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnDialogLongClickListener?) {
        adapter.onItemLongClickListener = listener
    }

    /**
     * Callback for showing more messages
     */
    interface LoadMoreCallback {
        fun onResult(dialogs: List<Dialog>)
    }

    interface LoadMoreListener {
        fun requestPreviousDialogs(
            count: Int,
            alreadyLoadedDialogsCount: Int,
            callback: LoadMoreCallback
        )
    }

    interface OnDialogClickListener {
        fun onClick(dialog: Dialog)
    }

    interface OnDialogLongClickListener {
        fun onLongClick(dialog: Dialog)
    }

    interface OnSwipeLeftListener {
        fun onAction(dialog: Dialog)
    }

    interface OnSwipeRightListener {
        fun onAction(dialog: Dialog)
    }
}