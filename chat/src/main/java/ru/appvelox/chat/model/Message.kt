package ru.appvelox.chat.model

import java.util.*

interface Message {
    fun getId(): Long
    fun getText(): String
    fun getAuthor(): Author
    fun getDate(): Date
    fun getStatus(): Status

    enum class Status {
        NONE, READ, SENT
    }
}