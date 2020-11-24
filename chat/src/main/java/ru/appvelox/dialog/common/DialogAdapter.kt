package ru.appvelox.dialog.common

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val index = dialogsList.indexOf(dialogsList.find { it.id == dialog.id })
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

    fun deleteDialogs() {
        dialogsList.clear()
        notifyDataSetChanged()
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

    fun notifyAppearanceChanged() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)

        return DialogViewHolder(view, appearance, appearance.getDateFormatter())
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val dialog = dialogsList[position]

        val view = holder.itemView

        if (dialog.unreadMessagesCount > 0) {
            applyUnreadStyle(view)
        } else {
            applyDefaultStyle(view)
        }

        if (onItemClickListener == null) {
            view.findViewById<ViewGroup>(R.id.dialogContainer).setOnClickListener(null)
        } else {
            view.findViewById<ViewGroup>(R.id.dialogContainer).setOnClickListener {
                onItemClickListener?.onClick(dialog)
            }
        }

        if (onItemLongClickListener == null) {
            view.findViewById<ViewGroup>(R.id.dialogContainer).setOnLongClickListener(null)
        } else {
            view.findViewById<ViewGroup>(R.id.dialogContainer).setOnLongClickListener {
                onItemLongClickListener?.onLongClick(dialog)
                true
            }
        }

        holder.bind(dialog)
    }

    private fun applyDefaultStyle(view: View) {
        view.setBackgroundColor(appearance.dialogItemBackground)

        val dialogName = view.findViewById<TextView>(R.id.dialogName)
        val dialogDate = view.findViewById<TextView>(R.id.dialogDate)
        val lastMessage = view.findViewById<TextView>(R.id.lastMessage)

        dialogName.setTextColor(appearance.dialogNameTextColor)
        dialogName.setTypeface(Typeface.DEFAULT, appearance.dialogNameTypeface)
        dialogName.textSize = appearance.dialogNameTextSize

        dialogDate.setTextColor(appearance.dialogDateColor)
        dialogDate.setTypeface(Typeface.DEFAULT, appearance.dialogDateTypeface)
        dialogDate.textSize = appearance.dialogDateTextSize

        lastMessage.setTextColor(appearance.dialogMessageTextColor)
        lastMessage.setTypeface(Typeface.DEFAULT, appearance.dialogMessageTypeface)
        lastMessage.textSize = appearance.dialogMessageTextSize
    }

    private fun applyUnreadStyle(view: View) {
        view.setBackgroundColor(appearance.dialogUnreadItemBackground)

        val dialogName = view.findViewById<TextView>(R.id.dialogName)
        val dialogDate = view.findViewById<TextView>(R.id.dialogDate)
        val lastMessage = view.findViewById<TextView>(R.id.lastMessage)
        val messagesCounter = view.findViewById<TextView>(R.id.unreadMessagesCounter)

        dialogName.setTextColor(appearance.dialogUnreadNameTextColor)
        dialogName.setTypeface(Typeface.DEFAULT, appearance.dialogUnreadNameTypeface)
        dialogName.textSize = appearance.dialogUnreadNameTextSize

        dialogDate.setTextColor(appearance.dialogUnreadDateColor)
        dialogDate.setTypeface(Typeface.DEFAULT, appearance.dialogUnreadDateTypeface)
        dialogDate.textSize = appearance.dialogUnreadDateTextSize

        lastMessage.setTextColor(appearance.dialogUnreadMessageTextColor)
        lastMessage.setTypeface(Typeface.DEFAULT, appearance.dialogUnreadMessageTypeface)
        lastMessage.textSize = appearance.dialogUnreadMessageTextSize

        val counterBackground = messagesCounter.background as GradientDrawable
        counterBackground.setColor(appearance.messagesCounterBackgroundColor)
    }

    override fun getItemCount(): Int {
        return dialogsList.size
    }
}