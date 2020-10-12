package ru.appvelox.chat.model

interface TextMessage : Message {
    fun getRepliedMessage(): TextMessage?
}