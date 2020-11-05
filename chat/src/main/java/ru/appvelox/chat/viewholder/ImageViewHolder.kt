package ru.appvelox.chat.viewholder

import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_message.view.*
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.common.ChatAppearance
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.utils.DateFormatter

/**
 * ViewHolder for [ImageMessage]
 */
class ImageViewHolder(
    view: View,
    appearance: ChatAppearance,
    dateFormatter: DateFormatter
) :
    MessageViewHolder(view, appearance, dateFormatter) {
    override fun bind(
        message: Message,
        showMessageDate: Boolean,
        messageType: MessageType?
    ) {
        super.bind(message, showMessageDate, messageType)

        val imageMessage = message as ImageMessage

        itemView.dateContainer.visibility = if (showMessageDate) View.VISIBLE else View.GONE

        Picasso.get()
            .load(imageMessage.getImageUrl())
            .into(itemView.image)
    }
}