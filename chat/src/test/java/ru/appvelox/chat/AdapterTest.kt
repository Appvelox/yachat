package ru.appvelox.chat

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.appvelox.chat.model.TextMessage

internal class AdapterTest {
    var appearance = mockk<ChatAppearance>()
    var adapter = spyk(MessageAdapter(appearance))

    @Before


    fun init(){
        appearance = mockk()
        adapter = spyk(MessageAdapter(appearance))
    }

    @Test
    fun addNewMessage_messageAddedToMessageList() {
        val message = mockk<TextMessage>()

        every { adapter.notifyItemInserted(any()) } returns Unit

        val messageCountBeforeAddition = adapter.messageList.size
        adapter.addNewMessage(message)
        val messageCountAfterAddition = adapter.messageList.size

        Assert.assertEquals(messageCountAfterAddition, messageCountBeforeAddition + 1)
    }

    @Test
    fun addNewMessage_adapterNotified() {
        val message = mockk<TextMessage>()

        every { adapter.notifyItemInserted(any()) } returns Unit

        adapter.addNewMessage(message)

        verify(exactly = 1) { adapter.notifyItemInserted(any()) }
    }


    @Test
    fun addOldMessages_adapterNotified() {
        val messages = listOf(mockk<TextMessage>(), mockk(),mockk())

        every { adapter.notifyMessagesInserted(any())} returns Unit

        adapter.addOldMessages(messages)

        verify(exactly = 1) { adapter.notifyMessagesInserted(any()) }
    }

    @Test
    fun addOldMessages_messagesAddedToMessageList() {
        val messages = listOf(mockk<TextMessage>(), mockk(),mockk())

        every { adapter.notifyMessagesInserted(any())} returns Unit

        val messageCountBeforeAddition = adapter.messageList.count()
        adapter.addOldMessages(messages)
        val messageCountAfterAddition = adapter.messageList.count()

        Assert.assertEquals(messageCountAfterAddition, messageCountBeforeAddition + messages.size)
    }

    @Test
    fun changeMessageSelection_messageAddedInSelectedIfNotSelected() {
        val message = mockk<TextMessage>()

        every { adapter.notifyItemChanged(any(), any())} returns Unit

        adapter.changeMessageSelection(message)

        Assert.assertTrue(adapter.selectedMessageList.contains(message))
    }

    @Test
    fun changeMessageSelection_messageRemovedFromSelectedIfSelected() {
        val message = mockk<TextMessage>()

        every { adapter.notifyItemChanged(any(), any())} returns Unit

        adapter.changeMessageSelection(message)
        adapter.changeMessageSelection(message)

        Assert.assertFalse(adapter.selectedMessageList.contains(message))
    }

    @Test
    fun changeMessageSelection_adapterNotified() {
        val message = mockk<TextMessage>()

        every { adapter.notifyItemChanged(any(), any())} returns Unit

        adapter.changeMessageSelection(message)

        verify(exactly = 1) { adapter.notifyItemChanged(any(), any()) }
    }


}