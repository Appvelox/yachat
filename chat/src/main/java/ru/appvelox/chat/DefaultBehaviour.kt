package ru.appvelox.chat

class DefaultBehaviour : Behaviour {
    override var onMessageClickListener: ChatView.OnMessageClickListener? = null
    override var onMessageLongClickListener: ChatView.OnMessageLongClickListener? = null
    override var onAvatarClickListener: ChatView.OnAvatarClickListener? = null
    override var onReplyClickListener: ChatView.OnReplyClickListener? = null
    override var onSwipeActionListener: ChatView.OnSwipeActionListener? = null
    override var navigateToRepliedMessage: Boolean = true
}