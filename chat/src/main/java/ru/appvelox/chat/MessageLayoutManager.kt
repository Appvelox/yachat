package ru.appvelox.chat

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageLayoutManager(context: Context) : LinearLayoutManager(context) {
    init {
        stackFromEnd = true
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return 2
    }
}