package ru.appvelox.dialog.common

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import ru.appvelox.chat.R
import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.chat.utils.DefaultDateFormatter

class CommonDialogAppearance(val context: Context) : DialogAppearance {
    override var dialogNameTextSize: Float = 18f
    override var dialogDateTextSize: Float = 14f
    override var dialogMessageTextSize: Float = 16f

    override var dialogUnreadNameTextSize: Float = 18f
    override var dialogUnreadDateTextSize: Float = 14f
    override var dialogUnreadMessageTextSize: Float = 16f

    override var dialogNameTypeface = Typeface.BOLD
    override var dialogDateTypeface = Typeface.NORMAL
    override var dialogMessageTypeface = Typeface.NORMAL

    override var dialogUnreadNameTypeface = Typeface.BOLD
    override var dialogUnreadDateTypeface = Typeface.NORMAL
    override var dialogUnreadMessageTypeface= Typeface.NORMAL

    override var dialogItemBackground = ContextCompat.getColor(context, R.color.transparent)
    override var dialogUnreadItemBackground= ContextCompat.getColor(context, R.color.transparent)
    override var messagesCounterBackgroundColor = ContextCompat.getColor(context, R.color.circle_color)

    override var dialogNameTextColor = ContextCompat.getColor(context, R.color.dark_gray)
    override var dialogDateColor = ContextCompat.getColor(context, R.color.dark_gray)
    override var dialogMessageTextColor = ContextCompat.getColor(context, R.color.dark_gray)

    override var dialogUnreadNameTextColor = ContextCompat.getColor(context, R.color.dark_gray)
    override var dialogUnreadDateColor = ContextCompat.getColor(context, R.color.dark_gray)
    override var dialogUnreadMessageTextColor = ContextCompat.getColor(context, R.color.dark_gray)

    override var messagesCounterEnabled: Boolean = true
    override var lastAuthorAvatarEnabled: Boolean = true
    override var dialogDividerEnabled: Boolean = true

    override var defaultPhoto = R.drawable.avatar_default

    private var dateFormatter: DateFormatter? = null
    override fun getDateFormatter(): DateFormatter {
        dateFormatter?.let {
            return it
        }
        return DefaultDateFormatter()
    }
}