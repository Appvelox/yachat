package ru.appvelox.chat.viewholder

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.R
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

        itemView.findViewById<ConstraintLayout>(R.id.dateContainer).visibility =
            if (showMessageDate) View.VISIBLE else View.GONE

        Picasso.get()
            .load(imageMessage.imageUrl)
            .into(itemView.findViewById<ImageView>(R.id.image))
    }
}