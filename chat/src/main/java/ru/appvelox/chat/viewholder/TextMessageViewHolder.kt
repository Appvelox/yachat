package ru.appvelox.chat.viewholder

import android.view.View
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.MessageType
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

        itemView.replyContainer?.let {
            if (replyMessage == null) {
                itemView.replyContainer.visibility = View.GONE
            } else {
                itemView.replyContainer.visibility = View.VISIBLE
                itemView.replyAuthorName.text = replyMessage.author.name
                itemView.replyMessage.text = replyMessage.text
            }
        }
    }
}