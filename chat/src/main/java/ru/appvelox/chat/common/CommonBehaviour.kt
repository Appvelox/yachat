package ru.appvelox.chat.common

import ru.appvelox.chat.Behaviour
import ru.appvelox.chat.ChatView

class CommonBehaviour : Behaviour {
    override var onMessageClickListener: ChatView.OnMessageClickListener? = null
    override var onMessageLongClickListener: ChatView.OnMessageLongClickListener? = null
    override var onAvatarClickListener: ChatView.OnAvatarClickListener? = null
    override var onReplyClickListener: ChatView.OnReplyClickListener? = null
    override var onSwipeActionListener: ChatView.OnSwipeActionListener? = null
    override var navigateToRepliedMessage: Boolean = true
}