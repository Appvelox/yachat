package ru.appvelox.chat.viewholder

import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.avatar.view.*
import kotlinx.android.synthetic.main.item_image_message.view.*
import ru.appvelox.chat.ChatAppearance
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.utils.CircularAvatar
import ru.appvelox.chat.utils.RoundedRectImage

class ImageViewHolder(
    view: View,
    appearance: ChatAppearance,
    dateFormatter: ChatView.DateFormatter,
    private val radius: Float,
    private val minWidth: Int,
    private val minHeight: Int,
    private val maxWidth: Int,
    private val maxHeight: Int
) :
    MessageViewHolder(view, appearance, dateFormatter) {
    override fun bind(
        message: Message,
        showMessageDate: Boolean,
        messageType: MessageType?
    ) {
        super.bind(message, showMessageDate, messageType)

        val imageMessage = message as ImageMessage

        itemView.authorName.text = imageMessage.getAuthor().getName()
        itemView.time.text = dateFormatter.formatTime(imageMessage.getDate())
        itemView.date.text = dateFormatter.formatDate(imageMessage.getDate())

        itemView.avatar?.let {
            Picasso.get()
                .load(imageMessage.getAuthor().getAvatar())
                .transform(CircularAvatar())
                .into(it)
        }

        itemView.dateContainer.visibility = if (showMessageDate) View.VISIBLE else View.GONE

        val transformation = RoundedRectImage(radius, minWidth, minHeight, maxWidth, maxHeight)

        Picasso.get()
            .load(imageMessage.getImageUrl())
            .transform(transformation)
            .into(itemView.image)
    }
}