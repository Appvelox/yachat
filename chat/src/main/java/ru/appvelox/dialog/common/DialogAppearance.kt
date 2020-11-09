package ru.appvelox.dialog.common

import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.dialog.DialogView

/**
 * Dialogs appearance for [DialogView]
 */
interface DialogAppearance {
    var defaultPhoto: Int

    fun getDateFormatter(): DateFormatter
}