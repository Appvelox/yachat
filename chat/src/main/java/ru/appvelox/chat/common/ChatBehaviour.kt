package ru.appvelox.chat.common

import ru.appvelox.chat.ChatView

/**
 * Listeners for [ChatView]
 */
interface ChatBehaviour {
    val onMessageClickListener: ChatView.OnMessageClickListener?
    val onMessageLongClickListener: ChatView.OnMessageLongClickListener?
    val onAvatarClickListener: ChatView.OnAvatarClickListener?
    val onReplyClickListener: ChatView.OnReplyClickListener?
    val onSwipeActionListener: ChatView.OnSwipeActionListener?
    val loadMoreListener: ChatView.LoadMoreListener?
    val navigateToRepliedMessage: Boolean
}