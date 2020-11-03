package ru.appvelox.chat.viewholder

import android.view.View
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.common.ChatAppearance
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage

/**
 * ViewHolder for [TextMessage]
 */
class TextMessageViewHolder(
    view: View,
    appearance: ChatAppearance,
    dateFormatter: ChatView.DateFormatter
) :
    MessageViewHolder(view, appearance, dateFormatter) {

    override fun bind(message: Message, showMessageDate: Boolean, messageType: MessageType?) {
        super.bind(message, showMessageDate, messageType)

        val textMessage = message as TextMessage

        val replyMessage = textMessage.getRepliedMessage()

        itemView.replyContainer?.let {
            if (replyMessage == null) {
                itemView.replyContainer.visibility = View.GONE
            } else {
                itemView.replyContainer.visibility = View.VISIBLE
                itemView.replyAuthorName.text = replyMessage.getAuthor().getName()
                itemView.replyMessage.text = replyMessage.getText()
            }
        }
    }
}