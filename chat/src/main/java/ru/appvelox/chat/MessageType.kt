package ru.appvelox.chat

import java.lang.IllegalArgumentException

enum class MessageType(val type: Int) {
    INCOMING_TEXT(0), OUTGOING_TEXT(1), INCOMING_IMAGE(2), OUTGOING_IMAGE(3);
}

fun Int.toMessageType(): MessageType {
    val typesMap = MessageType.values().associateBy { it.type }
    return typesMap[this] ?: throw IllegalArgumentException()
}