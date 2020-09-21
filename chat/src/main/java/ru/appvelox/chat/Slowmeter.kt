package ru.appvelox.chat

import android.util.Log
import java.lang.Exception

object Slowmeter {
    private var startTime: Long? = null

    fun from(){
       startTime = System.currentTimeMillis()
    }

    fun to(){
        if(startTime == null)
            throw Exception()

        startTime?.let { startTime ->
            Log.d("slowmeter", "Measured time = ${System.currentTimeMillis() - startTime} ms.")
        }
        startTime = null
    }
}