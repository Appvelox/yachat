package ru.appvelox.chat.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utils for transforming [Date] into [String]
 */
interface DateFormatter {
    fun formatDate(date: Date): String
    fun formatTime(date: Date): String
}

class DefaultDateFormatter : DateFormatter {
    override fun formatDate(date: Date): String {
        return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(date)
    }

    override fun formatTime(date: Date): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
    }
}