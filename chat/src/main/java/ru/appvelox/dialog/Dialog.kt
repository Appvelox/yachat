package ru.appvelox.dialog

import ru.appvelox.chat.model.Author
import ru.appvelox.chat.model.Message
import java.util.*

interface Dialog {
    fun getName(): String
    fun getId(): Long
    fun getPhoto(): String?
    fun getTime(): Date
    fun getAuthors(): List<Author>
    fun getLastMessage(): Message
    fun getUnreadMessagesCount(): Int
}