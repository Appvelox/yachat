package ru.appvelox.chat.viewholder

import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.avatar.view.*
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.common.ChatAppearance
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import ru.appvelox.chat.utils.CircularAvatar

/**
 * ViewHolder for [TextMessage]
 */
class TextMessageViewHolder(
    view: View,
    appearance: ChatAppearance,
    dateFormatter: ChatView.DateFormatter
) :
    MessageViewHolder(view, appearance, dateFormatter) {

    init {
        view.imageViewLeftSwipeActionIcon?.imageAlpha = 0
    }

    override fun bind(message: Message, showMessageDate: Boolean, messageType: MessageType?) {
        super.bind(message, showMessageDate, messageType)

        val textMessage = message as TextMessage

        itemView.authorName.text = textMessage.getAuthor().getName()
        itemView.message.text = textMessage.getText()
        itemView.time.text = dateFormatter.formatTime(textMessage.getDate())
        itemView.date.text = dateFormatter.formatDate(textMessage.getDate())

        itemView.avatar?.let {
            Picasso.get()
                .load(textMessage.getAuthor().getAvatar())
                .transform(CircularAvatar())
                .into(it)
        }

        itemView.dateContainer.visibility = if (showMessageDate) View.VISIBLE else View.GONE

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