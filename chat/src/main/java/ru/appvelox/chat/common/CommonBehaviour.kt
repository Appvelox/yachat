package ru.appvelox.chat.common

import ru.appvelox.chat.ChatView

/**
 * Default realization of ChatView [ChatBehaviour]
 */
class CommonBehaviour : ChatBehaviour {
    override var onMessageClickListener: ChatView.OnMessageClickListener? = null
    override var onMessageLongClickListener: ChatView.OnMessageLongClickListener? = null
    override var onAvatarClickListener: ChatView.OnAvatarClickListener? = null
    override var onReplyClickListener: ChatView.OnReplyClickListener? = null
    override var onSwipeActionListener: ChatView.OnSwipeActionListener? = null
    override var loadMoreListener: ChatView.LoadMoreListener? = null
    override var onMessageSelectedListener: ChatView.OnMessageSelectedListener? = null
    override var onImageClickListener: ChatView.OnImageClickListener? = null
    override var navigateToRepliedMessage: Boolean = true
}