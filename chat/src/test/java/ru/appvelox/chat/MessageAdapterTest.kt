package ru.appvelox.chat

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.appvelox.chat.model.TextMessage

class MessageAdapterTest {
    private lateinit var appearance: ChatAppearance
    private lateinit var adapter: MessageAdapter
    private lateinit var behaviour: ChatBehaviour

    private val message = mockk<TextMessage>()
    private val messages = listOf(mockk<TextMessage>(), mockk(), mockk())

    @Before
    fun init() {
        appearance = mockk()
        behaviour = mockk(relaxed = true)
        adapter = spyk(MessageAdapter(appearance, behaviour))
    }

    @Test
    fun addNewMessage_messageAddedToMessageList() {
        every { adapter.notifyItemInserted(any()) } returns Unit

        val messageCountBeforeAddition = adapter.messageList.size
        adapter.addNewMessage(message)
        val messageCountAfterAddition = adapter.messageList.size

        assertEquals(messageCountAfterAddition, messageCountBeforeAddition + 1)
    }

    @Test
    fun addNewMessage_adapterNotified() {
        every { adapter.notifyItemInserted(any()) } returns Unit

        adapter.addNewMessage(message)

        verify(exactly = 1) { adapter.notifyItemInserted(any()) }
    }

    @Test
    fun addOldMessages_adapterNotified() {
        every { adapter.notifyMessagesInserted(any()) } returns Unit

        adapter.addOldMessages(messages)

        verify(exactly = 1) { adapter.notifyMessagesInserted(any()) }
    }

    @Test
    fun addOldMessages_messagesAddedToMessageList() {
        every { adapter.notifyMessagesInserted(any()) } returns Unit

        val messageCountBeforeAddition = adapter.messageList.count()
        adapter.addOldMessages(messages)
        val messageCountAfterAddition = adapter.messageList.count()

        assertEquals(messageCountAfterAddition, messageCountBeforeAddition + messages.size)
    }

    @Test
    fun changeMessageSelection_messageAddedInSelectedIfNotSelected() {
        every { adapter.notifyItemChanged(any(), any()) } returns Unit

        adapter.changeMessageSelection(message)

        assertTrue(adapter.selectedMessageList.contains(message))
    }

    @Test
    fun changeMessageSelection_messageRemovedFromSelectedIfSelected() {
        every { adapter.notifyItemChanged(any(), any()) } returns Unit

        adapter.changeMessageSelection(message)
        adapter.changeMessageSelection(message)

        assertFalse(adapter.selectedMessageList.contains(message))
    }

    @Test
    fun changeMessageSelection_adapterNotified() {
        every { adapter.notifyItemChanged(any(), any()) } returns Unit

        adapter.changeMessageSelection(message)

        verify(exactly = 1) { adapter.notifyItemChanged(any(), any()) }
    }

    @Test
    fun setOnItemClickListener_adapterNotified() {
        every { adapter.notifyDataSetChanged() } returns Unit

        adapter.onItemClickListener = mockk()

        verify(exactly = 1) { adapter.notifyDataSetChanged() }
    }

    @Test
    fun setOnItemLongClickListener_adapterNotified() {
        every { adapter.notifyDataSetChanged() } returns Unit

        adapter.onItemLongClickListener = mockk()

        verify(exactly = 1) { adapter.notifyDataSetChanged() }
    }

    @Test
    fun deleteMessage_adapterNotified() {
        every { adapter.notifyItemRemoved(any()) } returns Unit

        adapter.messageList.add(message)

        adapter.deleteMessage(message)

        verify(exactly = 1) { adapter.notifyItemRemoved(any()) }
    }

    @Test
    fun updateMessage_adapterNotified() {
        every { adapter.notifyItemChanged(any()) } returns Unit
        every { message.getId() } returns 0

        adapter.messageList.add(message)

        adapter.updateMessage(message)

        verify(exactly = 1) { adapter.notifyItemChanged(any()) }
    }

    @Test
    fun addMessages_adapterNotified() {
        every { adapter.notifyDataSetChanged() } returns Unit

        adapter.addMessages(messages.toMutableList())

        verify(exactly = 1) { adapter.notifyDataSetChanged() }
    }
}