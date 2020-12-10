package ru.appvelox.dialog

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.appvelox.chat.R
import ru.appvelox.chat.utils.CircularAvatar
import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.dialog.common.DialogAppearance

/**
 * ViewHolder for [Dialog]
 */
class DialogViewHolder(
    val view: View,
    val appearance: DialogAppearance,
    private val dateFormatter: DateFormatter
) :
    RecyclerView.ViewHolder(view) {

    lateinit var dialog: Dialog

    fun bind(dialog: Dialog) {
        this.dialog = dialog

        val avatar = itemView.findViewById<ImageView>(R.id.avatar)
        val lastAuthorAvatar = itemView.findViewById<ImageView>(R.id.lastAuthorAvatar)
        val unreadMessagesCounter = itemView.findViewById<TextView>(R.id.unreadMessagesCounter)

        if (dialog.photo.isNullOrEmpty()) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(avatar)
        } else {
            Picasso.get()
                .load(dialog.photo)
                .transform(CircularAvatar())
                .into(avatar)
        }

        itemView.findViewById<TextView>(R.id.dialogName).text = dialog.name
        itemView.findViewById<TextView>(R.id.dialogDate).text =
            dateFormatter.formatTime(dialog.date)

        if (dialog.lastMessage.author.avatar.isNullOrEmpty()) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(lastAuthorAvatar)
        } else {
            Picasso.get()
                .load(dialog.lastMessage.author.avatar)
                .transform(CircularAvatar())
                .into(lastAuthorAvatar)
        }

        lastAuthorAvatar.visibility =
            if (appearance.lastAuthorAvatarEnabled) View.VISIBLE else View.GONE

        itemView.findViewById<TextView>(R.id.lastMessage).text = dialog.lastMessage.text

        if (dialog.unreadMessagesCount == 0 || !appearance.messagesCounterEnabled) {
            unreadMessagesCounter.visibility = View.GONE
        } else {
            unreadMessagesCounter.text = dialog.unreadMessagesCount.toString()
        }

        itemView.findViewById<View>(R.id.dialogDivider).visibility =
            if (appearance.dialogDividerEnabled) View.VISIBLE else View.GONE
    }
}