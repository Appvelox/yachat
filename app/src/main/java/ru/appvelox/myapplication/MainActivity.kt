package ru.appvelox.myapplication

import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ru.appvelox.chat.*
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val listener = object : ChatView.LoadMoreListener {
        override fun requestPreviousMessages(
            count: Int,
            alreadyLoadedMessagesCount: Int,
            callback: ChatView.LoadMoreCallback
        ) {
            val messages = mutableListOf<Message>()
            repeat(count) {
                messages.add(MessageGenerator.generateMessage(true))
            }
            AsyncTask.execute {
//                Thread.sleep(500)
                callback.onResult(messages)
            }
        }
    }

    var counter = 0
        get() {
            return field++
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatView.setLoadMoreListener(listener)

        chatView.setCurrentUserId(MessageGenerator.user1.getId())

        chatView.addMessage(MessageGenerator.generateMessage(false))
//        chatView.addMessage(MessageGenerator.generateMessage(false))
//        chatView.addMessage(MessageGenerator.generateMessage(false))

        message.setText(MessageGenerator.generateMessageText())

        send.setOnClickListener {
            sendMessage()
        }

        sendContainer.setOnClickListener {
            sendMessage()
        }


//        val initialMessages = mutableListOf<TextMessage>()
//        for(counter in 0..50)
//            initialMessages.add(MessageGenerator.generateMessage(false, textMessage.text.toString()))

//        chatView.addMessages(initialMessages)

        setTheme2()

        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.parseColor("#FF061F3D"))
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))

        toolbar.setOnClickListener {
            setRandomTheme()
        }

//        chatView.setLayout(R.layout.item_custom_incoming_message, R.layout.item_custom_outgoing_message)
    }

    fun sendMessage(){
        if(message.text.toString().isEmpty())
            message.setText(MessageGenerator.generateMessageText())
        chatView.addMessage(MessageGenerator.generateMessage(false, message.text.toString()))
        message.setText(MessageGenerator.generateMessageText())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (counter == 0)
            setTheme1()
        else {
            setTheme2()
            counter = 0
        }

        return super.onOptionsItemSelected(item)
    }

    fun setTheme2() {

        val color1 = Color.parseColor("#FFF3F9FF")
//        val color1 = Color.parseColor("#000000")
        val color2 = Color.parseColor("#EFfffD")
        val color3 = Color.parseColor("#3B98D6")
        val color4 = Color.parseColor("#176EA8")
        val colorSelect = Color.parseColor("#FFDCDAFF")

        val textColor1 = color3
        val textColor2 = Color.parseColor("#000000")
        val textColor3 = Color.parseColor("#56437A")
        val textColor4 = Color.parseColor("#000000")

        val textSize1 = 14f
        val textSize2 = 16f
        val textSize3 = 15f
        val textSize4 = 14f

        val backgroundColor = Color.parseColor("#0A000000")
        chatView.setBackgroundColor(backgroundColor)


        val radius = 30f

        chatView.setMessageBackgroundCornerRadius(radius)
        chatView.setSelectOnClick(Random.nextBoolean())

        chatView.setAuthorTextSize(textSize1)
        chatView.setMessageTextSize(textSize2)
        chatView.setReplyAuthorTextSize(textSize3)
        chatView.setReplyMessageTextSize(textSize4)

        chatView.setAuthorTextColor(textColor1)
        chatView.setMessageTextColor(textColor2)
        chatView.setReplyAuthorTextColor(textColor3)
        chatView.setReplyMessageTextColor(textColor4)

        chatView.setReplyLineColor(color4)

        chatView.setIncomingMessageBackgroundColor(color1)
        chatView.setOutgoingMessageBackgroundColor(color2)
        chatView.setIncomingSelectedMessageBackgroundColor(color1)
        chatView.setOutgoingSelectedMessageBackgroundColor(color1)
        chatView.setIncomingSelectedMessageBackgroundColor(colorSelect)
        chatView.setOutgoingSelectedMessageBackgroundColor(colorSelect)

//        chatView.setMaxWidth(800)

        chatView.setSelectOnClick(true)
    }

    fun setTheme1() {

        val color1 = Color.parseColor("#FCFFFC")
        val color2 = Color.parseColor("#EFFFF9")
        val color3 = Color.parseColor("#B26C9F")
        val color4 = Color.parseColor("#176EA8")

        val textColor1 = Color.parseColor("#D5001741")
        val textColor2 = Color.parseColor("#23001A")
        val textColor3 = Color.parseColor("#99000000")
        val textColor4 = Color.parseColor("#B0000000")

        val textSize1 = 16f
        val textSize2 = 16f
        val textSize3 = 12f
        val textSize4 = 15f

        val backgroundColor = Color.parseColor("#FFFFFF")
        chatView.setBackgroundResource(R.drawable.wallpaper3)


        val radius = 40f

        chatView.setMessageBackgroundCornerRadius(radius)
        chatView.setSelectOnClick(Random.nextBoolean())

        chatView.setAuthorTextSize(textSize1)
        chatView.setMessageTextSize(textSize2)
        chatView.setReplyAuthorTextSize(textSize3)
        chatView.setReplyMessageTextSize(textSize4)

        chatView.setAuthorTextColor(textColor1)
        chatView.setMessageTextColor(textColor2)
        chatView.setReplyAuthorTextColor(textColor3)
        chatView.setReplyMessageTextColor(textColor4)

        chatView.setReplyLineColor(color4)

        chatView.setIncomingMessageBackgroundColor(color1)
        chatView.setOutgoingMessageBackgroundColor(color2)
        chatView.setIncomingSelectedMessageBackgroundColor(color1)
        chatView.setOutgoingSelectedMessageBackgroundColor(color1)

//        chatView.setMaxWidth(400)

        chatView.setSelectOnClick(false)
    }

    fun setRandomTheme() {
        chatView.setMessageBackgroundCornerRadius(Random.nextInt(100).toFloat())
        chatView.setSelectOnClick(Random.nextBoolean())

        chatView.setAuthorTextSize(12f + Random.nextInt(8))
        chatView.setMessageTextSize(12f + Random.nextInt(8))
        chatView.setReplyAuthorTextSize(12f + Random.nextInt(8))
        chatView.setReplyMessageTextSize(12f + Random.nextInt(8))

        chatView.setAuthorTextColor(Color.argb(255, Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)))
        chatView.setMessageTextColor(Color.argb(255, Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)))
        chatView.setReplyAuthorTextColor(Color.argb(255, Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)))
        chatView.setReplyMessageTextColor(
            Color.argb(
                255,
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )

        chatView.setIncomingMessageBackgroundColor(
            Color.argb(
                255,
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )
        chatView.setOutgoingMessageBackgroundColor(
            Color.argb(
                255,
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )
        chatView.setIncomingSelectedMessageBackgroundColor(
            Color.argb(
                255,
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )
        chatView.setOutgoingSelectedMessageBackgroundColor(
            Color.argb(
                255,
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )

//        chatView.setMaxWidth(Random.nextInt(1000))
//        chatView.setMinWidth(Random.nextInt(1000))

        chatView.setSelectOnClick(true)
    }
}