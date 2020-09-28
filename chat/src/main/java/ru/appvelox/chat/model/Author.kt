package ru.appvelox.chat.model

interface Author {
    fun getName(): String
    fun getId(): Long
    fun getAvatar(): String
}