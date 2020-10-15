package ru.appvelox.chat

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.appvelox.chat.common.*
import ru.appvelox.chat.model.Author
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import java.util.*

/**
 * Component for displaying messages
 */
class ChatView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private var adapter: MessageAdapter =
        CommonMessageAdapter(CommonAppearance(context), CommonBehaviour())

    fun setOnItemClickListener(listener: OnMessageClickListener?) {
        adapter.onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnMessageLongClickListener?) {
        adapter.onItemLongClickListener = listener
    }

    fun setLoadMoreListener(listener: LoadMoreListener?) {
        adapter.loadMoreListener = listener
    }

    init {
        super.setAdapter(adapter)
        val layoutManager = MessageLayoutManager(context)
        super.setLayoutManager(layoutManager)
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findFirstVisibleItemPosition() <= 5 && !adapter.oldDataLoading) {
                    adapter.requestPreviousMessagesFromListener()
                }
            }
        })
        val swipeToReplyCallback = SwipeToReplyCallback()
        val itemTouchHelper = ItemTouchHelper(swipeToReplyCallback)
        itemTouchHelper.attachToRecyclerView(this)
        swipeToReplyCallback.listener = object : OnSwipeActionListener {
            override fun onAction(textMessage: Message) {
                Toast.makeText(
                    context,
                    "Reply on textMessage #${textMessage.getId()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        adapter.onReplyClickListener = object : OnReplyClickListener {
            override fun onReplyClick(textMessage: Message) {
                navigateToMessage(textMessage)
            }
        }
    }

    fun setSelectOnClick(isSelectable: Boolean) {
        if (isSelectable)
            adapter.onItemClickListener = object : OnMessageClickListener {
                override fun onClick(textMessage: Message) {
                    adapter.changeMessageSelection(textMessage)
                }
            }
        else {
            adapter.onItemClickListener = null
            adapter.eraseSelectedMessages()
        }
    }

    fun addMessage(textMessage: Message) {
        adapter.addNewMessage(textMessage)
        layoutManager?.scrollToPosition(adapter.getLastMessageIndex())
    }

    fun setCurrentUserId(id: Long) {
        adapter.currentUserId = id
    }

    fun navigateToMessage(textMessage: Message) {
        val scrollTo = adapter.getPositionOfMessage(textMessage)
        layoutManager?.scrollToPosition(scrollTo)
    }

    fun addOldMessages(textMessages: List<TextMessage>) {
        adapter.addOldMessages(textMessages)
    }

    fun deleteMessage(textMessage: TextMessage) {
        adapter.deleteMessage(textMessage)
    }

    fun updateMessage(textMessage: TextMessage) {
        adapter.updateMessage(textMessage)
    }

    fun setMessageBackgroundCornerRadius(radius: Float) {
        adapter.appearance.messageBackgroundCornerRadius = radius
        adapter.notifyAppearanceChanged()
    }

    fun setIncomingMessageBackgroundColor(color: Int) {
        adapter.appearance.incomingMessageBackgroundColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setOutgoingMessageBackgroundColor(color: Int) {
        adapter.appearance.outgoingMessageBackgroundColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setIncomingSelectedMessageBackgroundColor(color: Int) {
        adapter.appearance.incomingSelectedMessageBackgroundColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setOutgoingSelectedMessageBackgroundColor(color: Int) {
        adapter.appearance.outgoingSelectedMessageBackgroundColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setMessageTextSize(size: Float) {
        adapter.appearance.messageTextSize = size
        adapter.notifyAppearanceChanged()
    }

    fun setAuthorTextSize(size: Float) {
        adapter.appearance.authorNameSize = size
        adapter.notifyAppearanceChanged()
    }

    fun setReplyMessageTextSize(size: Float) {
        adapter.appearance.replyMessageSize = size
        adapter.notifyAppearanceChanged()
    }

    fun setReplyAuthorTextSize(size: Float) {
        adapter.appearance.replyAuthorNameSize = size
        adapter.notifyAppearanceChanged()
    }

    fun setAuthorTextColor(color: Int) {
        adapter.appearance.authorNameColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setMessageTextColor(color: Int) {
        adapter.appearance.messageColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setReplyAuthorTextColor(color: Int) {
        adapter.appearance.replyAuthorNameColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setReplyMessageTextColor(color: Int) {
        adapter.appearance.replyMessageColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setReplyLineColor(color: Int) {
        adapter.appearance.replyLineColor = color
        adapter.notifyAppearanceChanged()
    }

    fun setMaxWidth(width: Int) {
        adapter.appearance.maxMessageWidth = width
        adapter.notifyAppearanceChanged()
    }

    fun setMinWidth(width: Int) {
        adapter.appearance.minMessageWidth = width
        adapter.notifyAppearanceChanged()
    }

    fun notifyDatasetChanged() {
        adapter.notifyDataSetChanged()
    }

    fun addMessages(messages: MutableList<Message>) {
        adapter.addMessages(messages)
    }

    /**
     * Sets custom layouts for [MessageAdapter]
     */
    fun setLayout(incomingMessageLayout: Int?, outgoingMessageLayout: Int?) {
        val currentAppearance = adapter.appearance
        val currentBehaviour = adapter.behaviour
        val oldAdapter = adapter

        adapter = if (incomingMessageLayout == null || outgoingMessageLayout == null)
            CommonMessageAdapter(currentAppearance, currentBehaviour)
        else
            MessageAdapter(currentAppearance, currentBehaviour)

        oldAdapter.copyPropertiesTo(adapter)

        setAdapter(adapter)

        (adapter.appearance as CommonAppearance).setMessageLayout(
            incomingMessageLayout,
            outgoingMessageLayout
        )
        adapter.notifyAppearanceChanged()
    }

    /**
     * Callback for showing more messages
     */
    interface LoadMoreCallback {
        fun onResult(textMessages: List<Message>)
    }

    interface LoadMoreListener {
        fun requestPreviousMessages(
            count: Int,
            alreadyLoadedMessagesCount: Int,
            callback: LoadMoreCallback
        )
    }

    interface OnMessageClickListener {
        fun onClick(textMessage: Message)
    }

    interface OnReplyClickListener {
        fun onReplyClick(textMessage: Message)
    }

    interface OnSwipeActionListener {
        fun onAction(textMessage: Message)
    }

    interface OnMessageLongClickListener {
        fun onLongClick(textMessage: Message)
    }

    interface OnAvatarClickListener {
        fun onClick(author: Author)
    }

    interface DateFormatter {
        fun formatDate(date: Date): String
        fun formatTime(date: Date): String
    }
}
