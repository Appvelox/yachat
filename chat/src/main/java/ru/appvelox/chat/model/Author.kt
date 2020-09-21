package ru.appvelox.chat.model

import android.graphics.Bitmap

interface Author {
    fun getName(): String
    fun getId(): Long
    fun getAvatar(): String
}