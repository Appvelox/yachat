package ru.appvelox.chat.common

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import org.joda.time.Days
import ru.appvelox.chat.ChatView
import ru.appvelox.chat.MessageType
import ru.appvelox.chat.R
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import ru.appvelox.chat.toMessageType
import ru.appvelox.chat.viewholder.ImageViewHolder
import ru.appvelox.chat.viewholder.MessageViewHolder
import ru.appvelox.chat.viewholder.TextMessageViewHolder

/**
 * Base adapter for [ChatView]
 */
open class MessageAdapter(
    val appearance: ChatAppearance,
    val behaviour: ChatBehaviour,
    initTextMessages: List<TextMessage>? = null
) :
    RecyclerView.Adapter<MessageViewHolder>() {

    var onReplyClickListener = behaviour.onReplyClickListener

    var loadMoreListener = behaviour.loadMoreListener

    var currentUserId: Long? = null

    var oldDataLoading = false

    var onItemClickListener = behaviour.onMessageClickListener
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    var onItemLongClickListener = behaviour.onMessageLongClickListener
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    var onMessageSelectedListener = behaviour.onMessageSelectedListener

    val messageList = mutableListOf<Message>().apply {
        if (initTextMessages != null)
            addAll(initTextMessages)
    }

    val selectedMessageList = mutableListOf<Message>()

    fun copyPropertiesTo(adapter: MessageAdapter) {
        adapter.onReplyClickListener = onReplyClickListener
        adapter.loadMoreListener = loadMoreListener
        adapter.currentUserId = currentUserId
        adapter.oldDataLoading = oldDataLoading
        adapter.onItemClickListener = onItemClickListener
        adapter.onItemLongClickListener = onItemLongClickListener

        adapter.messageList.clear()
        adapter.messageList.addAll(messageList)

        adapter.selectedMessageList.clear()
        adapter.selectedMessageList.addAll(selectedMessageList)
    }

    fun addNewMessage(message: Message) {
        messageList.add(message)
        notifyItemInserted(messageList.indexOf(message))
    }

    fun addOldMessages(messages: List<Message>) {
        messages.forEach { message ->
            messageList.add(0, message)
        }

        notifyMessagesInserted(messages)
    }

    fun notifyMessagesInserted(messages: List<Message>) {
        Handler(Looper.getMainLooper()).post {
            notifyItemRangeInserted(
                messageList.indexOf(messages.last()),
                messages.size
            )
            oldDataLoading = false
        }
    }

    fun changeMessageSelection(message: Message) {
        if (selectedMessageList.contains(message))
            selectedMessageList.remove(message)
        else
            selectedMessageList.add(message)

        notifyItemChanged(messageList.indexOf(message), null)
    }

    fun eraseSelectedMessages() {
        val messages = mutableListOf<Message>()

        selectedMessageList.forEach { messages.add(it) }

        selectedMessageList.clear()

        messages.forEach { notifyItemChanged(messageList.indexOf(it)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val layout = when (viewType) {
            MessageType.INCOMING_TEXT.type -> appearance.incomingTextMessageLayout
            MessageType.OUTGOING_TEXT.type -> appearance.outgoingTextMessageLayout
            MessageType.INCOMING_IMAGE.type -> appearance.incomingImageMessageLayout
            MessageType.OUTGOING_IMAGE.type -> appearance.outgoingImageMessageLayout
            else -> appearance.outgoingTextMessageLayout
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return when (viewType) {
            MessageType.INCOMING_TEXT.type, MessageType.OUTGOING_TEXT.type -> TextMessageViewHolder(
                view,
                appearance,
                appearance.getDateFormatter()
            )
            MessageType.INCOMING_IMAGE.type, MessageType.OUTGOING_IMAGE.type -> ImageViewHolder(
                view,
                appearance,
                appearance.getDateFormatter(),
                appearance.messageBackgroundCornerRadius,
                appearance.minImageMessageWidth,
                appearance.minImageMessageHeight,
                appearance.maxImageMessageWidth,
                appearance.maxImageMessageHeight
            )
            else -> TextMessageViewHolder(view, appearance, appearance.getDateFormatter())
        }
    }

    override fun getItemCount() = messageList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val message = messageList[position]

        val view = holder.itemView

        if (onItemClickListener == null) {
            view.findViewById<ViewGroup>(R.id.messageContainer).setOnClickListener(null)
        } else {
            view.findViewById<ViewGroup>(R.id.messageContainer).setOnClickListener {
                onItemClickListener?.onClick(message)
            }
        }

        if (onItemLongClickListener == null) {
            view.findViewById<ViewGroup>(R.id.messageContainer).setOnLongClickListener(null)
        } else {
            view.findViewById<ViewGroup>(R.id.messageContainer).setOnLongClickListener {
                onItemLongClickListener?.onLongClick(message)
                true
            }
        }

        if (onReplyClickListener == null) {
            view.findViewById<ViewGroup>(R.id.replyContainer).setOnClickListener(null)
        } else {
            view.findViewById<ViewGroup>(R.id.replyContainer).setOnClickListener {
                (message as TextMessage).getRepliedMessage()?.let {
                    onReplyClickListener?.onReplyClick(it)
                }
            }
        }

        if (position == 0) {
            holder.bind(message, true, getItemViewType(position).toMessageType())
            return
        }

        val previousMessage = messageList[position - 1]
        val messageDate = DateTime(message.getDate()).withTimeAtStartOfDay()
        val previousMessageDate = DateTime(previousMessage.getDate()).withTimeAtStartOfDay()
        val daysBetweenMessages = Days.daysBetween(messageDate, previousMessageDate).days
        val showMessageDate = daysBetweenMessages != 0

        holder.bind(message, showMessageDate, getItemViewType(position).toMessageType())
    }

    fun getLastMessageIndex(): Int {
        return messageList.lastIndex
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (message.isIncoming()) {
            if (message is TextMessage)
                MessageType.INCOMING_TEXT.type
            else
                MessageType.INCOMING_IMAGE.type
        } else {
            if (message is TextMessage)
                MessageType.OUTGOING_TEXT.type
            else
                MessageType.OUTGOING_IMAGE.type
        }
    }

    fun Message.isIncoming(): Boolean {
        val messageAuthorId = getAuthor().getId()
        return messageAuthorId != currentUserId
    }

    fun requestPreviousMessagesFromListener() {
        loadMoreListener?.requestPreviousMessages(
            20,
            messageList.size,
            object : ChatView.LoadMoreCallback {
                override fun onResult(textMessages: List<Message>) {
                    oldDataLoading = true
                    addOldMessages(textMessages)
                }
            })
    }

    fun notifyAppearanceChanged() {
        notifyDataSetChanged()
    }

    fun getPositionOfMessage(message: Message): Int {
        return messageList.indexOf(message)
    }

    fun deleteMessage(message: Message) {
        val position = messageList.indexOf(message)
        messageList.remove(message)
        notifyItemRemoved(position)
    }

    fun updateMessage(message: Message) {
        val index = messageList.indexOf(messageList.find { it.getId() == message.getId() })
        messageList[index] = message
        notifyItemChanged(index)
    }

    fun addMessages(messages: MutableList<Message>) {
        messageList.addAll(messages)
        notifyDataSetChanged()
    }
}