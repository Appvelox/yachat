package ru.appvelox.chat.viewholder

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.R
import ru.appvelox.chat.common.ChatAppearance
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import ru.appvelox.chat.utils.DateFormatter

/**
 * ViewHolder for [TextMessage]
 */
class TextMessageViewHolder(
    view: View,
    appearance: ChatAppearance,
    dateFormatter: DateFormatter
) :
    MessageViewHolder(view, appearance, dateFormatter) {

    override fun bind(message: Message, showMessageDate: Boolean, messageType: MessageType?) {
        super.bind(message, showMessageDate, messageType)

        val textMessage = message as TextMessage

        val replyMessage = textMessage.repliedMessage
        val replyContainer = itemView.findViewById<FrameLayout>(R.id.replyContainer)

        replyContainer?.let {
            if (replyMessage == null) {
                replyContainer.visibility = View.GONE
            } else {
                replyContainer.visibility = View.VISIBLE
                itemView.findViewById<TextView>(R.id.replyAuthorName).text =
                    replyMessage.author.name
                itemView.findViewById<TextView>(R.id.replyMessage).text = replyMessage.text
            }
        }
    }
}