package ru.appvelox.chat.model

interface ImageMessage : Message {
    fun getImageUrl(): String?
}