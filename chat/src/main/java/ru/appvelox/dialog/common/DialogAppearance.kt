package ru.appvelox.dialog.common

import android.graphics.Typeface
import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.dialog.DialogView

/**
 * Dialogs appearance for [DialogView]
 */
interface DialogAppearance {

    // Size
    var dialogNameTextSize: Float
    var dialogDateTextSize: Float
    var dialogMessageTextSize: Float

    var dialogUnreadNameTextSize: Float
    var dialogUnreadDateTextSize: Float
    var dialogUnreadMessageTextSize: Float

    // Typeface
    var dialogNameTypeface: Int
    var dialogDateTypeface: Int
    var dialogMessageTypeface: Int

    var dialogUnreadNameTypeface: Int
    var dialogUnreadDateTypeface: Int
    var dialogUnreadMessageTypeface: Int

    // Color
    var dialogItemBackground: Int
    var dialogUnreadItemBackground: Int
    var messagesCounterBackgroundColor: Int

    var dialogNameTextColor: Int
    var dialogDateColor: Int
    var dialogMessageTextColor: Int

    var dialogUnreadNameTextColor: Int
    var dialogUnreadDateColor: Int
    var dialogUnreadMessageTextColor: Int

    // Visibility
    var messagesCounterEnabled: Boolean
    var lastAuthorAvatarEnabled: Boolean
    var dialogDividerEnabled: Boolean

    var defaultPhoto: Int

    fun getDateFormatter(): DateFormatter
}