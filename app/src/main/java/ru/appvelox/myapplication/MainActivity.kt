package ru.appvelox.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.appvelox.chat.ChatInput
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.model.Message
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var isTheme1 = false

    lateinit var optionsMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatView.setLoadMoreListener(object : ChatView.LoadMoreListener {
            override fun requestPreviousMessages(
                count: Int,
                alreadyLoadedMessagesCount: Int,
                callback: ChatView.LoadMoreCallback
            ) {
                val messages = mutableListOf<Message>()
                repeat(count) {
                    messages.add(
                        MessageGenerator.generateMessage(
                            true,
                            MessageGenerator.generateMessageText()
                        )
                    )
                }
                callback.onResult(messages)
            }
        })

        chatView.setDefaultAvatar(R.drawable.avatar)

        chatView.setCurrentUserId(MessageGenerator.user1.getId())

        chatView.addMessage(
            MessageGenerator.generateMessage(
                false,
                MessageGenerator.generateMessageText()
            )
        )

        chatView.setOnMessageSelectedListener(object : ChatView.OnMessageSelectedListener {
            override fun onSelected(selected: Boolean) {
                if (selected) {
                    showMenuIcons()
                } else {
                    hideMenuIcons()
                }
            }
        })

        chatInput.setOnSendButtonClickListener(object : ChatInput.OnSendButtonClickListener {
            override fun onClick(input: CharSequence?) {
                chatView.addMessage(MessageGenerator.generateMessage(false, input.toString()))
                chatInput.setText(MessageGenerator.generateMessageText())
            }
        })

        chatInput.setOnImageMenuItemClickListener(object : ChatInput.OnImageMenuItemClickListener {
            override fun onClick() {
                chatView.addMessage(
                    MessageGenerator.generateImageMessage(
                        false,
                        MessageGenerator.generateMessageText()
                    )
                )
            }
        })

        chatView.setOnImageClickListener(object : ChatView.OnImageClickListener {
            override fun onClick(imageUrl: String) {
                val bundle = Bundle().apply {
                    putString("photo", imageUrl)
                }

                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(android.R.id.content, PhotoFragment().apply {
                        arguments = bundle
                    })
                    .commit()
            }
        })

        chatInput.setText(MessageGenerator.generateMessageText())

        chatInput.setMaxLines(4)

        setTheme2()

        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.parseColor("#FF061F3D"))
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (menu != null) {
            optionsMenu = menu
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeTheme -> {
                isTheme1 = if (isTheme1) {
                    setTheme2()
                    false
                } else {
                    setTheme1()
                    true
                }
            }
            R.id.copy -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("copy", chatView.getSelectedMessagesText())
                clipboard.setPrimaryClip(clip)

                chatView.eraseSelectedMessages()
                hideMenuIcons()
            }
            R.id.delete -> {
                chatView.deleteSelectedMessages()

                chatView.eraseSelectedMessages()
                hideMenuIcons()
            }
        }
        hideMenuIcons()

        return super.onOptionsItemSelected(item)
    }

    fun showMenuIcons() {
        optionsMenu.findItem(R.id.copy).isVisible = true
        optionsMenu.findItem(R.id.delete).isVisible = true
    }

    fun hideMenuIcons() {
        optionsMenu.findItem(R.id.copy).isVisible = false
        optionsMenu.findItem(R.id.delete).isVisible = false
    }

    private fun setTheme1() {
        val color1 = getColorCompat(R.color.colorBackground1_1)
        val color2 = getColorCompat(R.color.colorBackground2_1)
        val color3 = getColorCompat(R.color.colorReply_1)

        val textColor1 = getColorCompat(R.color.colorText1_1)
        val textColor2 = getColorCompat(R.color.colorText2_1)
        val textColor3 = getColorCompat(R.color.colorText3_1)
        val textColor4 = getColorCompat(R.color.colorText4_1)

        val textSize1 = 16f
        val textSize2 = 12f
        val textSize3 = 15f

        chatView.setBackgroundResource(R.drawable.wallpaper3)

        val radius = 30f

        chatView.setMessageBackgroundCornerRadius(radius)
        chatView.setSelectOnClick(Random.nextBoolean())

        chatView.setAuthorTextSize(textSize1)
        chatView.setMessageTextSize(textSize1)
        chatView.setReplyAuthorTextSize(textSize2)
        chatView.setReplyMessageTextSize(textSize3)

        chatView.setAuthorTextColor(textColor1)
        chatView.setMessageTextColor(textColor2)
        chatView.setReplyAuthorTextColor(textColor3)
        chatView.setReplyMessageTextColor(textColor4)

        chatView.setReplyLineColor(color3)

        chatView.setIncomingMessageBackgroundColor(color1)
        chatView.setOutgoingMessageBackgroundColor(color2)
        chatView.setIncomingSelectedMessageBackgroundColor(color1)
        chatView.setOutgoingSelectedMessageBackgroundColor(color1)

        chatView.setMaxWidth(600)

        chatView.setSelectOnClick(false)
    }

    private fun setTheme2() {
        val color1 = getColorCompat(R.color.colorBackground1_2)
        val color2 = getColorCompat(R.color.colorBackground2_2)
        val color3 = getColorCompat(R.color.colorReply_2)
        val colorSelect = getColorCompat(R.color.colorSelect_2)

        val textColor1 = getColorCompat(R.color.colorText1_2)
        val textColor2 = getColorCompat(R.color.colorText2_2)
        val textColor3 = getColorCompat(R.color.colorText3_2)

        val textSize1 = 14f
        val textSize2 = 16f
        val textSize3 = 15f

        val backgroundColor = getColorCompat(R.color.colorBackground3_2)
        chatView.setBackgroundColor(backgroundColor)

        val radius = 40f

        chatView.setMessageBackgroundCornerRadius(radius)
        chatView.setSelectOnClick(Random.nextBoolean())

        chatView.setAuthorTextSize(textSize1)
        chatView.setMessageTextSize(textSize2)
        chatView.setReplyAuthorTextSize(textSize3)
        chatView.setReplyMessageTextSize(textSize1)

        chatView.setAuthorTextColor(textColor1)
        chatView.setMessageTextColor(textColor2)
        chatView.setReplyAuthorTextColor(textColor3)
        chatView.setReplyMessageTextColor(textColor2)

        chatView.setReplyLineColor(color3)

        chatView.setIncomingMessageBackgroundColor(color1)
        chatView.setOutgoingMessageBackgroundColor(color2)
        chatView.setIncomingSelectedMessageBackgroundColor(color1)
        chatView.setOutgoingSelectedMessageBackgroundColor(color1)
        chatView.setIncomingSelectedMessageBackgroundColor(colorSelect)
        chatView.setOutgoingSelectedMessageBackgroundColor(colorSelect)

        chatView.setMaxWidth(800)

        chatView.setSelectOnClick(true)
    }

    @Suppress("DEPRECATION")
    private fun getColorCompat(colorId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(colorId, null)
        } else {
            resources.getColor(colorId)
        }
    }
}