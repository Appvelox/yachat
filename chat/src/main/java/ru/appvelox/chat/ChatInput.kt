package ru.appvelox.chat

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.chat_input.view.*

/**
 * Component for sending messages
 */
class ChatInput(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), TextWatcher {

    private var sendButtonClickListener: OnSendButtonClickListener? = null
    private var imageMenuItemClickListener: OnImageMenuItemClickListener? = null
    private var fileMenuItemClickListener: OnFileMenuItemClickListener? = null

    private var input: CharSequence? = null

    init {
        val view = inflate(context, R.layout.chat_input, this)

        message.addTextChangedListener(this)

        send.isEnabled = !input.isNullOrEmpty()

        send.setOnClickListener {
            if (send.isEnabled) {
                sendButtonClickListener?.onClick(input)
            }
        }

        sendContainer.setOnClickListener {
            send.callOnClick()
        }

        attach.setOnClickListener {
            val popup = PopupMenu(context, view)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.attachImage -> {
                        imageMenuItemClickListener?.onClick()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.attachFile -> {
                        fileMenuItemClickListener?.onClick()
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.inflate(R.menu.attach)
            popup.show()
        }

        attachContainer.setOnClickListener {
            attach.callOnClick()
        }
    }

    fun setOnSendButtonClickListener(listener: OnSendButtonClickListener) {
        sendButtonClickListener = listener
    }

    fun setOnImageMenuItemClickListener(listener: OnImageMenuItemClickListener) {
        imageMenuItemClickListener = listener
    }

    fun setOnFileMenuItemClickListener(listener: OnFileMenuItemClickListener) {
        fileMenuItemClickListener = listener
    }

    fun setText(text: String) {
        message.setText(text)
    }

    interface OnSendButtonClickListener {
        fun onClick(input: CharSequence?)
    }

    interface OnImageMenuItemClickListener {
        fun onClick()
    }

    interface OnFileMenuItemClickListener {
        fun onClick()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    /**
     * Tracking EditText changes
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        input = s
        send.isEnabled = !input.isNullOrEmpty()
    }

    override fun afterTextChanged(s: Editable?) {
    }
}