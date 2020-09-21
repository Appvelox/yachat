package ru.appvelox.chat

interface Behaviour {
    val onMessageClickListener: ChatView.OnMessageClickListener?
    val onMessageLongClickListener: ChatView.OnMessageLongClickListener?
    val onAvatarClickListener: ChatView.OnAvatarClickListener?
    val onReplyClickListener: ChatView.OnReplyClickListener?
    val onSwipeActionListener: ChatView.OnSwipeActionListener?
    val navigateToRepliedMessage: Boolean
}