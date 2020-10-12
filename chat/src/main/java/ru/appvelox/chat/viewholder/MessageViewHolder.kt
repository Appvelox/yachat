package ru.appvelox.chat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.ChatAppearance
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.model.Message

abstract class MessageViewHolder(
    view: View,
    private val appearance: ChatAppearance,
    protected val dateFormatter: ChatView.DateFormatter
) :
    RecyclerView.ViewHolder(view) {
    var message: Message? = null
    open fun bind(
        message: Message,
        showMessageDate: Boolean,
        messageType: MessageType? = null
    ) {
        this.message = message
        updateStatusIndicator()
    }

    private fun updateStatusIndicator() {
        val message = message ?: return

        if (message.getStatus() == Message.Status.NONE) {
            itemView.statusIndicator.visibility = View.GONE
        }

        val icon = when (message.getStatus()) {
            Message.Status.READ -> appearance.getReadIndicatorIcon()
            Message.Status.SENT -> appearance.getSentIndicatorIcon()
            else -> appearance.getReadIndicatorIcon()
        }

        itemView.statusIndicator.setImageDrawable(icon)
    }
}