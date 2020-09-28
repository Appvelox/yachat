package ru.appvelox.chat.viewholder

import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_message.view.*
import ru.appvelox.chat.ChatAppearance
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.utils.ImageTransformation
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message

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

        val maxImageMessageWidth: Int = 600
        val maxImageMessageHeight: Int = 600 * 2
        val minImageMessageWidth: Int = 100
        val minImageMessageHeight: Int = minImageMessageWidth

        val transformation = ImageTransformation(radius, minWidth, minHeight, maxWidth, maxHeight)
//            when (messageType!!.type) {
//            MessageType.INCOMING_IMAGE.type -> IncomingImageTransformation(radius, minImageMessageWidth, minImageMessageHeight, maxImageMessageWidth, maxImageMessageHeight)
//            else -> OutgoingImageTransformation(radius, minImageMessageWidth, minImageMessageHeight, maxImageMessageWidth, maxImageMessageHeight)
//        }

        Picasso.get()
            .load(imageMessage.getImageUrl())
            .transform(transformation)
//            .networkPolicy(NetworkPolicy.NO_CACHE)
//            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(itemView.image)

    }
}