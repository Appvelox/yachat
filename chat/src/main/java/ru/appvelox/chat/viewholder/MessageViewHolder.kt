package ru.appvelox.chat.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.R
import ru.appvelox.chat.common.ChatAppearance
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.utils.CircularAvatar
import ru.appvelox.chat.utils.DateFormatter

/**
 * Base ViewHolder for [Message]
 */
abstract class MessageViewHolder(
    val view: View,
    private val appearance: ChatAppearance,
    private val dateFormatter: DateFormatter
) :
    RecyclerView.ViewHolder(view) {
    var message: Message? = null
    open fun bind(
        message: Message,
        showMessageDate: Boolean,
        messageType: MessageType? = null
    ) {
        this.message = message

        updateStatusIndicator()

        view.findViewById<ImageView>(R.id.imageViewLeftSwipeActionIcon)?.imageAlpha = 0

        itemView.findViewById<TextView>(R.id.authorName).text = message.author.name
        itemView.findViewById<TextView>(R.id.message).text = message.text
        itemView.findViewById<TextView>(R.id.time).text = dateFormatter.formatTime(message.date)
        itemView.findViewById<TextView>(R.id.date).text = dateFormatter.formatDate(message.date)

        if (message.author.avatar.isNullOrEmpty()) {
            Picasso.get()
                .load(appearance.defaultAvatar)
                .transform(CircularAvatar())
                .into(itemView.findViewById<ImageView>(R.id.avatar))
        } else {
            Picasso.get()
                .load(message.author.avatar)
                .transform(CircularAvatar())
                .into(itemView.findViewById<ImageView>(R.id.avatar))
        }

        itemView.findViewById<ConstraintLayout>(R.id.dateContainer).visibility =
            if (showMessageDate) View.VISIBLE else View.GONE
    }

    private fun updateStatusIndicator() {
        val message = message ?: return

        if (message.status == Message.Status.NONE) {
            itemView.findViewById<ImageView>(R.id.statusIndicator).visibility = View.GONE
        }

        val icon = when (message.status) {
            Message.Status.READ -> appearance.getReadIndicatorIcon()
            Message.Status.SENT -> appearance.getSentIndicatorIcon()
            else -> appearance.getReadIndicatorIcon()
        }

        itemView.findViewById<ImageView>(R.id.statusIndicator).setImageDrawable(icon)
    }
}