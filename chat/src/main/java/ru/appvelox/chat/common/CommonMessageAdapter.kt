package ru.appvelox.chat.common

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import ru.appvelox.chat.*
import ru.appvelox.chat.model.TextMessage
import ru.appvelox.chat.viewholder.MessageViewHolder

/**
 * Default realization of ChatView [MessageAdapter]
 */
class CommonMessageAdapter(
    appearance: ChatAppearance,
    behaviour: ChatBehaviour,
    initTextMessages: List<TextMessage>? = null
) :
    MessageAdapter(appearance, behaviour, initTextMessages) {

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val message = messageList[position]

        applyStyle(holder.itemView, getItemViewType(position).toMessageType())

        if (selectedMessageList.contains(message))
            if (message.isIncoming())
                applyIncomingSelectedAppearance(holder.itemView)
            else
                applyOutgoingSelectedAppearance(holder.itemView)
        else
            if (message.isIncoming())
                applyIncomingAppearance(holder.itemView)
            else
                applyOutgoingAppearance(holder.itemView)
    }

    private fun applyStyle(view: View, messageType: MessageType) {
        applyCommonStyle(view)

        when (messageType) {
            MessageType.INCOMING_TEXT -> applyIncomingTextMessageAppearance(view)
            MessageType.INCOMING_IMAGE -> applyIncomingImageMessageAppearance(view)
            MessageType.OUTGOING_TEXT -> applyOutgoingTextMessageAppearance(view)
            MessageType.OUTGOING_IMAGE -> applyOutgoingImageMessageAppearance(view)
        }
    }

    private fun applyIncomingTextMessageAppearance(view: View) {
        applyIncomingConstraints(view)
        applyIncomingAppearance(view)
        applyTextTimeStyle(view)
        applyReplyAuthorNameStyle(view)
    }

    private fun applyIncomingImageMessageAppearance(view: View) {
        applyIncomingConstraints(view)
        applyIncomingAppearance(view)
        applyImageTimeStyle(view)
    }

    private fun applyOutgoingTextMessageAppearance(view: View) {
        applyOutgoingConstraints(view)
        applyOutgoingAppearance(view)
        applyTextTimeStyle(view)
        applyReplyAuthorNameStyle(view)
    }

    private fun applyOutgoingImageMessageAppearance(view: View) {
        applyOutgoingConstraints(view)
        applyOutgoingAppearance(view)
        applyImageTimeStyle(view)
    }

    private fun applyOutgoingAppearance(view: View) {
        val avatarContainer = view.findViewById<View>(R.id.avatarContainer)
        val authorName = view.findViewById<View>(R.id.authorName)
        val messageContainer = view.findViewById<View>(R.id.messageContainer)

        avatarContainer.visibility =
            if (appearance.isOutgoingAvatarVisible) View.VISIBLE else View.GONE
        authorName.visibility =
            if (appearance.isOutgoingAuthorNameVisible) View.VISIBLE else View.GONE
        messageContainer.background = appearance.getOutgoingMessageBackground()
    }

    private fun applyIncomingAppearance(view: View) {
        val avatarContainer = view.findViewById<View>(R.id.avatarContainer)
        val authorName = view.findViewById<View>(R.id.authorName)
        val messageContainer = view.findViewById<View>(R.id.messageContainer)

        avatarContainer.visibility =
            if (appearance.isIncomingAvatarVisible) View.VISIBLE else View.GONE
        authorName.visibility =
            if (appearance.isIncomingAuthorNameVisible) View.VISIBLE else View.GONE
        messageContainer.background = appearance.getIncomingMessageBackground()
    }

    private fun applyCommonStyle(view: View) {
        val date = view.findViewById<TextView>(R.id.date)
        val authorName = view.findViewById<TextView>(R.id.authorName)
        val messageText = view.findViewById<TextView?>(R.id.message)
        val time = view.findViewById<TextView>(R.id.time)
        val replyAuthorName = view.findViewById<TextView?>(R.id.replyAuthorName)
        val replyMessage = view.findViewById<TextView?>(R.id.replyMessage)
        val replyLine = view.findViewById<View?>(R.id.replyLine)

        val imageViewLeftSwipeActionIcon =
            view.findViewById<ImageView>(R.id.imageViewLeftSwipeActionIcon)
        val messageContainer = view.findViewById<ConstraintLayout>(R.id.messageContainer)

        date.setTextColor(appearance.dateTextColor)
        date.textSize = appearance.dateTextSize

        authorName.setTextColor(appearance.authorNameColor)
        authorName.textSize = appearance.authorNameSize

        messageText?.setTextColor(appearance.messageColor)
        messageText?.textSize = appearance.messageTextSize

        time.textSize = appearance.timeTextSize

        replyAuthorName?.setTextColor(appearance.replyAuthorNameColor)
        replyAuthorName?.textSize = appearance.replyAuthorNameSize

        replyMessage?.setTextColor(appearance.replyMessageColor)
        replyMessage?.textSize = appearance.replyMessageSize

        replyLine?.setBackgroundColor(appearance.replyLineColor)

        imageViewLeftSwipeActionIcon.setImageDrawable(appearance.getSwipeActionIcon())

        messageContainer.maxWidth = appearance.maxMessageWidth
        messageContainer.minWidth = appearance.minMessageWidth
    }

    private fun applyTextTimeStyle(view: View) {
        val time = view.findViewById<TextView>(R.id.time)
        time.setTextColor(appearance.timeTextColor)
    }

    private fun applyImageTimeStyle(view: View) {
        val time = view.findViewById<TextView>(R.id.time)
        time.setTextColor(appearance.imageTimeTextColor)
        val timeBackground = view.findViewById<ViewGroup>(R.id.timeContainer)
        timeBackground.background = appearance.getTimeBackground()
    }

    private fun applyReplyAuthorNameStyle(view: View) {
        val replyAuthorName = view.findViewById<View>(R.id.replyAuthorName)
        replyAuthorName.visibility =
            if (appearance.isOutgoingAuthorNameVisible) View.VISIBLE else View.GONE
    }

    private fun applyIncomingConstraints(view: View) {
        val contentContainer = view.findViewById<View>(R.id.contentContainer)
        val avatarContainer = view.findViewById<View>(R.id.avatarContainer)
        val messageContainer = view.findViewById<View>(R.id.messageContainer)
        val authorName = view.findViewById<View>(R.id.authorName)

        val constraintSet = ConstraintSet()
        constraintSet.clone(contentContainer as ConstraintLayout)
        constraintSet.setHorizontalBias(avatarContainer.id, 0f)
        constraintSet.setHorizontalBias(messageContainer.id, 0f)
        if (appearance.isIncomingAvatarVisible) {
            constraintSet.connect(
                messageContainer.id,
                ConstraintSet.START,
                avatarContainer.id,
                ConstraintSet.END
            )
        }
        constraintSet.applyTo(contentContainer)

        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(messageContainer as ConstraintLayout)
        constraintSet2.setHorizontalBias(authorName.id, 0f)
        constraintSet2.applyTo(messageContainer)
    }

    private fun applyOutgoingConstraints(view: View) {
        val contentContainer = view.findViewById<View>(R.id.contentContainer)
        val avatarContainer = view.findViewById<View>(R.id.avatarContainer)
        val messageContainer = view.findViewById<View>(R.id.messageContainer)
        val authorName = view.findViewById<View>(R.id.authorName)

        val constraintSet = ConstraintSet()
        constraintSet.clone(contentContainer as ConstraintLayout)
        constraintSet.setHorizontalBias(avatarContainer.id, 1f)
        constraintSet.setHorizontalBias(messageContainer.id, 1f)
        if (appearance.isOutgoingAvatarVisible) {
            constraintSet.connect(
                messageContainer.id,
                ConstraintSet.END,
                avatarContainer.id,
                ConstraintSet.START
            )
        }
        constraintSet.applyTo(contentContainer)

        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(messageContainer as ConstraintLayout)
        constraintSet2.setHorizontalBias(authorName.id, 1f)
        constraintSet2.applyTo(messageContainer)
    }

    private fun applyOutgoingSelectedAppearance(view: View) {
        val messageContainer = view.findViewById<View>(R.id.messageContainer)
        messageContainer.background = appearance.getOutgoingSelectedMessageBackground()
    }

    private fun applyIncomingSelectedAppearance(view: View) {
        val messageContainer = view.findViewById<View>(R.id.messageContainer)
        messageContainer.background = appearance.getIncomingSelectedMessageBackground()
    }
}