package ru.appvelox.dialog.common

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.appvelox.chat.R
import ru.appvelox.dialog.Dialog
import ru.appvelox.dialog.DialogView
import ru.appvelox.dialog.DialogViewHolder

class DialogAdapter(
    val appearance: DialogAppearance,
    behaviour: DialogBehaviour,
    private val initDialogs: List<Dialog>? = null
) :
    RecyclerView.Adapter<DialogViewHolder>() {

    private val dialogsList = mutableListOf<Dialog>().apply {
        if (initDialogs != null)
            addAll(initDialogs)
    }

    var loadMoreListener = behaviour.loadMoreListener

    var oldDataLoading = false

    var onItemClickListener = behaviour.onDialogClickListener
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    var onItemLongClickListener = behaviour.onDialogLongClickListener
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    fun addDialog(dialog: Dialog) {
        dialogsList.add(dialog)
        notifyItemInserted(dialogsList.indexOf(dialog))
    }

    fun deleteDialog(dialog: Dialog) {
        val position = dialogsList.indexOf(dialog)
        dialogsList.remove(dialog)
        notifyItemRemoved(position)
    }

    fun updateDialog(dialog: Dialog) {
        val index = dialogsList.indexOf(dialogsList.find { it.getId() == dialog.getId() })
        dialogsList[index] = dialog
        notifyItemChanged(index)
    }

    fun addDialogs(dialogs: List<Dialog>) {
        dialogsList.addAll(dialogs)
        notifyDataSetChanged()
    }

    fun addOldDialogs(dialogs: List<Dialog>) {
        dialogs.forEach { dialog ->
            dialogsList.add(0, dialog)
        }

        notifyDialogsInserted(dialogs)
    }

    fun requestPreviousMessagesFromListener() {
        loadMoreListener?.requestPreviousDialogs(
            20,
            dialogsList.size,
            object : DialogView.LoadMoreCallback {
                override fun onResult(dialogs: List<Dialog>) {
                    oldDataLoading = true
                    addOldDialogs(dialogs)
                }
            })
    }

    fun getLastDialogIndex(): Int {
        return dialogsList.lastIndex
    }

    private fun notifyDialogsInserted(dialogs: List<Dialog>) {
        Handler(Looper.getMainLooper()).post {
            notifyItemRangeInserted(
                dialogsList.indexOf(dialogs.last()),
                dialogs.size
            )
            oldDataLoading = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)

        return DialogViewHolder(view, appearance, appearance.getDateFormatter())
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val dialog = dialogsList[position]

        holder.bind(dialog)
    }

    override fun getItemCount(): Int {
        return dialogsList.size
    }
}