package ru.appvelox.dialog.common

import android.content.Context
import ru.appvelox.chat.R
import ru.appvelox.chat.utils.DateFormatter
import ru.appvelox.chat.utils.DefaultDateFormatter

class CommonDialogAppearance(val context: Context) : DialogAppearance {
    override var defaultPhoto = R.drawable.avatar_default

    private var dateFormatter: DateFormatter? = null
    override fun getDateFormatter(): DateFormatter {
        dateFormatter?.let {
            return it
        }
        return DefaultDateFormatter()
    }
}