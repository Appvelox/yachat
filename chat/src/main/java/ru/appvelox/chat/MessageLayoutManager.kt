package ru.appvelox.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MessageLayoutManager(context: Context): LinearLayoutManager(context){
    init {
        stackFromEnd = true
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return 2
    }
}