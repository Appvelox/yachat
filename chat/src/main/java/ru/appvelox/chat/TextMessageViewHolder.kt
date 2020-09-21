package ru.appvelox.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.avatar.view.*
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage

class TextMessageViewHolder(view: View, appearance: ChatAppearance, dateFormatter: ChatView.DateFormatter) :
    MessageViewHolder(view, appearance, dateFormatter) {

    init {
        view.imageViewLeftSwipeActionIcon?.imageAlpha = 0
    }

    override fun bind(message: Message, showMessageDate: Boolean, messageType: MessageType?) {
        super.bind(message, showMessageDate, messageType)

        val textMessage = this.message as TextMessage

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

        if (showMessageDate)
            itemView.dateContainer.visibility = View.VISIBLE
        else
            itemView.dateContainer.visibility = View.GONE

        val replyMessage = textMessage.getRepliedMessage()
        if (replyMessage == null) {
            if (itemView.replyContainer != null)
                itemView.replyContainer.visibility = View.GONE
        } else {
            if (itemView.replyContainer != null) {
                itemView.replyContainer.visibility = View.VISIBLE
                itemView.replyAuthorName.text = replyMessage.getAuthor().getName()
                itemView.replyMessage.text = replyMessage.getText()
            }
        }

    }
}