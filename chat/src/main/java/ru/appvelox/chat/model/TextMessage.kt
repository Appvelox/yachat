package ru.appvelox.chat.model

interface TextMessage : Message {
    var repliedMessage: Message?
}