package ru.appvelox.dialog

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.avatar.view.*
import kotlinx.android.synthetic.main.item_dialog.view.*
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

        if (dialog.photo.isNullOrEmpty()) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(itemView.avatar)
        } else {
            Picasso.get()
                .load(dialog.photo)
                .transform(CircularAvatar())
                .into(itemView.avatar)
        }

        itemView.dialogName.text = dialog.name
        itemView.dialogDate.text = dateFormatter.formatTime(dialog.date)

        if (dialog.lastMessage.author.avatar.isNullOrEmpty()) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(itemView.lastAuthorAvatar as ImageView)
        } else {
            Picasso.get()
                .load(dialog.lastMessage.author.avatar)
                .transform(CircularAvatar())
                .into(itemView.lastAuthorAvatar as ImageView)
        }

        itemView.lastAuthorAvatar.visibility =
            if (appearance.lastAuthorAvatarEnabled) View.VISIBLE else View.GONE

        itemView.lastMessage.text = dialog.lastMessage.text

        if (dialog.unreadMessagesCount == 0 || !appearance.messagesCounterEnabled) {
            itemView.unreadMessagesCounter.visibility = View.GONE
        } else {
            itemView.unreadMessagesCounter.text = dialog.unreadMessagesCount.toString()
        }

        itemView.dialogDivider.visibility =
            if (appearance.dialogDividerEnabled) View.VISIBLE else View.GONE
    }
}