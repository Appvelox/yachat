package ru.appvelox.dialog

import ru.appvelox.chat.model.Author
import ru.appvelox.chat.model.Message
import java.util.*

interface Dialog {
    var id: String
    var name: String
    var date: Date
    var authors: List<Author>
    var lastMessage: Message
    var unreadMessagesCount: Int
    var photo: String?
}