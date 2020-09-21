package ru.appvelox.chat

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import java.text.SimpleDateFormat
import java.util.*

internal class DefaultAppearance(val context: Context) : ChatAppearance {
    override var messageBackgroundCornerRadius = 0f
    override var incomingMessageBackgroundColor = Color.argb(255, 175, 224, 255)

    override var outgoingMessageBackgroundColor = Color.argb(255, 192, 221, 232)

    override var incomingSelectedMessageBackgroundColor = Color.argb(255, 98, 50, 43)
    override var outgoingSelectedMessageBackgroundColor = Color.argb(255, 98, 75, 180)

    override var replyAuthorNameColor = Color.DKGRAY
    override var replyMessageColor = Color.DKGRAY
    override var replyLineColor = Color.CYAN
    override var replyAuthorNameSize: Float = 15f
    override var replyMessageSize: Float = 14f

    override var authorNameColor = Color.GRAY
    override var messageColor = Color.BLUE
    override var authorNameSize: Float = 16f
    override var messageTextSize: Float = 15f


    override var dateTextSize: Float = 14f
    override var timeTextSize: Float = 12f
    override var dateTextColor: Int = Color.GRAY
    override var timeTextColor: Int = Color.DKGRAY
    override var imageTimeTextColor: Int = Color.WHITE

    override var maxMessageWidth: Int = 800
    override var minMessageWidth: Int = 200

    override var maxImageMessageWidth: Int = maxMessageWidth
    override var maxImageMessageHeight: Int = maxMessageWidth * 2
    override val minImageMessageWidth: Int = 100
    override val minImageMessageHeight: Int = minImageMessageWidth

    override var isIncomingAvatarVisible = false
    override var isOutgoingAvatarVisible = false
    override var isIncomingAuthorNameVisible = true
    override var isOutgoingAuthorNameVisible = true
    override var isIncomingReplyAuthorNameVisible = false
    override var isOutgoingReplyAuthorNameVisible = true
    override var isSwipeActionIconVisible = true

    val defaultMessageLayout = R.layout.item_message
    val defaultImageLayout = R.layout.item_image_message

    override var incomingTextMessageLayout: Int = defaultMessageLayout
    override var outgoingTextMessageLayout: Int = defaultMessageLayout
    override var incomingImageMessageLayout: Int = defaultImageLayout
    override var outgoingImageMessageLayout: Int = defaultImageLayout

    fun setMessageLayout(incomingMessageLayout: Int?, outgoingMessageLayout: Int?) {
        if (incomingMessageLayout == null) {
            this.incomingTextMessageLayout = defaultMessageLayout
        } else {
            this.incomingTextMessageLayout = incomingMessageLayout
        }

        if (outgoingMessageLayout == null) {
            this.outgoingTextMessageLayout = defaultMessageLayout
        } else {
            this.outgoingTextMessageLayout = outgoingMessageLayout
        }
    }

    override fun getIncomingMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(incomingMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        if (isInChain)
            cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
        else
            cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, radius, radius)
    }


    override fun getOutgoingMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(outgoingMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        if (isInChain)
            cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
        else
            cornerRadii = floatArrayOf(radius, radius, 0f, 0f, radius, radius, radius, radius)
    }


    override fun getIncomingSelectedMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(outgoingSelectedMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        if (isInChain)
            cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
        else
            cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, radius, radius)
    }

    override fun getOutgoingSelectedMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(incomingSelectedMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        if (isInChain)
            cornerRadii = floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
        else
            cornerRadii = floatArrayOf(radius, radius, 0f, 0f, radius, radius, radius, radius)
    }

    var swipeActionIconResource = R.drawable.ic_reply_black_24dp
    var readIcon = R.drawable.ic_done_all_black_24dp
    var sentIcon = R.drawable.ic_access_time_black_24dp
    var timeBackgroundCapsule = R.drawable.time_background_capsule

    override fun getSwipeActionIcon(): Drawable? {
        return context.resources.getDrawable(swipeActionIconResource)
    }

    override fun getReadIndicatorIcon(): Drawable? {
        return context.resources.getDrawable(readIcon)
    }

    override fun getSentIndicatorIcon(): Drawable? {
        return context.resources.getDrawable(sentIcon)
    }

    override fun getTimeBackground(): Drawable? {
        val drawable = AppCompatResources.getDrawable(context, timeBackgroundCapsule)?:return null
        val resultDrawable = DrawableCompat.setTint(drawable, Color.parseColor("#70000000"))
        return drawable
    }

    var mDateFormatter: ChatView.DateFormatter? = null
    private val defaultDateFormatter = object : ChatView.DateFormatter {
        override fun formatDate(date: Date): String {
            return SimpleDateFormat("dd MMMM").format(date)
        }

        override fun formatTime(date: Date): String {
            return SimpleDateFormat("HH:mm").format(date)
        }
    }

    override fun getDateFormatter(): ChatView.DateFormatter {
        mDateFormatter?.let {
            return it
        }
        return defaultDateFormatter
    }

}