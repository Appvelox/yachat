package ru.appvelox.chat.model

import java.util.*

interface TextMessage: Message {
    fun getRepliedMessage(): TextMessage?
}