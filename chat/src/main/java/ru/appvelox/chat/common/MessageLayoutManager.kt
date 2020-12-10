package ru.appvelox.chat.common

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * LayoutManager for ChatView
 */
class MessageLayoutManager(context: Context) : LinearLayoutManager(context) {
    init {
        stackFromEnd = true
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return 2
    }
}