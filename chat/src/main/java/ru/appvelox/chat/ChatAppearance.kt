package ru.appvelox.chat

import android.graphics.drawable.Drawable

interface ChatAppearance {

//    Size
    var messageBackgroundCornerRadius: Float
    var replyMessageSize: Float
    var replyAuthorNameSize: Float
    var messageTextSize: Float
    var authorNameSize: Float
    var maxMessageWidth: Int
    var minMessageWidth: Int

    var maxImageMessageHeight: Int
    val minImageMessageHeight: Int
    var maxImageMessageWidth: Int
    val minImageMessageWidth: Int

    var dateTextSize: Float
    var timeTextSize: Float

//    Color
    var outgoingSelectedMessageBackgroundColor: Int
    var incomingSelectedMessageBackgroundColor: Int
    var outgoingMessageBackgroundColor: Int
    var incomingMessageBackgroundColor: Int
    var replyLineColor: Int
    var replyMessageColor: Int
    var replyAuthorNameColor: Int
    var messageColor: Int
    var authorNameColor: Int
    var dateTextColor: Int
    var timeTextColor: Int
    var imageTimeTextColor: Int

//    Visibility
    var isIncomingAvatarVisible: Boolean
    var isOutgoingAvatarVisible: Boolean
    var isIncomingAuthorNameVisible: Boolean
    var isOutgoingAuthorNameVisible: Boolean
    var isIncomingReplyAuthorNameVisible: Boolean
    var isOutgoingReplyAuthorNameVisible: Boolean
    var isSwipeActionIconVisible: Boolean

    var incomingTextMessageLayout: Int
    var outgoingTextMessageLayout: Int
    var incomingImageMessageLayout: Int
    var outgoingImageMessageLayout: Int

    fun getOutgoingMessageBackground(isInChain: Boolean = false): Drawable?
    fun getIncomingMessageBackground(isInChain: Boolean = false): Drawable?
    fun getOutgoingSelectedMessageBackground(isInChain: Boolean = false): Drawable
    fun getIncomingSelectedMessageBackground(isInChain: Boolean = false): Drawable
    fun getSwipeActionIcon(): Drawable?
    fun getReadIndicatorIcon(): Drawable?
    fun getSentIndicatorIcon(): Drawable?
    fun getTimeBackground(): Drawable?

    fun getDateFormatter(): ChatView.DateFormatter

}