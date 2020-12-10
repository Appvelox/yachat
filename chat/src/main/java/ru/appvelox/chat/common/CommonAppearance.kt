package ru.appvelox.chat.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import ru.appvelox.chat.R
import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.chat.utils.DefaultDateFormatter

/**
 * Default realization of ChatView [ChatAppearance]
 */
class CommonAppearance(val context: Context) : ChatAppearance {
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

    override var isIncomingAvatarVisible = true
    override var isOutgoingAvatarVisible = false
    override var isIncomingAuthorNameVisible = true
    override var isOutgoingAuthorNameVisible = true
    override var isIncomingReplyAuthorNameVisible = false
    override var isOutgoingReplyAuthorNameVisible = true
    override var isSwipeActionIconVisible = true

    private val defaultMessageLayout = R.layout.item_message
    private val defaultImageLayout = R.layout.item_image_message

    override var incomingTextMessageLayout: Int = defaultMessageLayout
    override var outgoingTextMessageLayout: Int = defaultMessageLayout
    override var incomingImageMessageLayout: Int = defaultImageLayout
    override var outgoingImageMessageLayout: Int = defaultImageLayout

    override var defaultAvatar: Int = R.drawable.avatar_default

    override fun getIncomingMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(incomingMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        cornerRadii = if (isInChain)
            floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
        else
            floatArrayOf(0f, 0f, radius, radius, radius, radius, radius, radius)
    }

    override fun getOutgoingMessageBackground(isInChain: Boolean) = GradientDrawable().apply {
        setColor(outgoingMessageBackgroundColor)
        val radius = messageBackgroundCornerRadius
        cornerRadii = if (isInChain)
            floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
        else
            floatArrayOf(radius, radius, 0f, 0f, radius, radius, radius, radius)
    }

    override fun getIncomingSelectedMessageBackground(isInChain: Boolean) =
        GradientDrawable().apply {
            setColor(outgoingSelectedMessageBackgroundColor)
            val radius = messageBackgroundCornerRadius
            cornerRadii = if (isInChain)
                floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
            else
                floatArrayOf(0f, 0f, radius, radius, radius, radius, radius, radius)
        }

    override fun getOutgoingSelectedMessageBackground(isInChain: Boolean) =
        GradientDrawable().apply {
            setColor(incomingSelectedMessageBackgroundColor)
            val radius = messageBackgroundCornerRadius
            cornerRadii = if (isInChain)
                floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
            else
                floatArrayOf(radius, radius, 0f, 0f, radius, radius, radius, radius)
        }

    private var swipeActionIconResource = R.drawable.ic_reply_black_24dp
    private var readIcon = R.drawable.ic_done_all_black_24dp
    private var sentIcon = R.drawable.ic_access_time_black_24dp
    private var timeBackgroundCapsule = R.drawable.time_background_capsule

    override fun getSwipeActionIcon(): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, swipeActionIconResource, null)
    }

    override fun getReadIndicatorIcon(): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, readIcon, null)
    }

    override fun getSentIndicatorIcon(): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, sentIcon, null)
    }

    override fun getTimeBackground(): Drawable? {
        val drawable = AppCompatResources.getDrawable(context, timeBackgroundCapsule) ?: return null
        DrawableCompat.setTint(drawable, Color.parseColor("#70000000"))
        return drawable
    }

    private var dateFormatter: DateFormatter? = null
    override fun getDateFormatter(): DateFormatter {
        dateFormatter?.let {
            return it
        }
        return DefaultDateFormatter()
    }
}