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

    fun bind(dialog: Dialog) {
        if (dialog.getPhoto() == null) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(itemView.avatar)
        } else {
            Picasso.get()
                .load(dialog.getPhoto())
                .transform(CircularAvatar())
                .into(itemView.avatar)
        }

        itemView.dialogName.text = dialog.getName()
        itemView.dialogDate.text = dateFormatter.formatTime(dialog.getTime())

        if (dialog.getLastMessage().getAuthor().getAvatar() == null) {
            Picasso.get()
                .load(appearance.defaultPhoto)
                .transform(CircularAvatar())
                .into(itemView.lastAuthorAvatar as ImageView)
        } else {
            Picasso.get()
                .load(dialog.getLastMessage().getAuthor().getAvatar())
                .transform(CircularAvatar())
                .into(itemView.lastAuthorAvatar as ImageView)
        }

        itemView.lastAuthorAvatar.visibility =
            if (appearance.lastAuthorAvatarEnabled) View.VISIBLE else View.GONE

        itemView.lastMessage.text = dialog.getLastMessage().getText()

        if (dialog.getUnreadMessagesCount() == 0 || !appearance.messagesCounterEnabled) {
            itemView.unreadMessagesCounter.visibility = View.GONE
        } else {
            itemView.unreadMessagesCounter.text = dialog.getUnreadMessagesCount().toString()
        }

        itemView.dialogDivider.visibility =
            if (appearance.dialogDividerEnabled) View.VISIBLE else View.GONE
    }
}