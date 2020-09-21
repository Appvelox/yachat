package ru.appvelox.chat

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.item_image_message.view.*
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import java.lang.Exception

class ImageViewHolder(view: View, appearance: ChatAppearance, dateFormatter: ChatView.DateFormatter, private val radius: Float, val minWidth: Int, val minHeight: Int, val maxWidth: Int, val maxHeight: Int) :
    MessageViewHolder(view, appearance, dateFormatter) {
    override fun bind(
        message: Message,
        showMessageDate: Boolean,
        messageType: MessageType?
    ) {
        super.bind(message, showMessageDate, messageType)

//        val imageMessage = message as ImageMessage
        if (message !is ImageMessage)
            return

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
            .load(message.getImageUrl())
            .transform(transformation)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(itemView.image)

    }

}