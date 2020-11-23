package ru.appvelox.chat.model

import java.util.*

interface Message {
    var id: String
    var text: String
    var author: Author
    var date: Date
    val status: Status

    enum class Status {
        NONE, READ, SENT
    }
}