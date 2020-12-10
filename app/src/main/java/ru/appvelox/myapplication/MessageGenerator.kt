package ru.appvelox.myapplication

import ru.appvelox.chat.model.Author
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import ru.appvelox.dialog.Dialog
import java.util.*
import kotlin.random.Random

object MessageGenerator {

    val user1 = object : Author {
        override var id = 0L.toString()
        override var name = "Emma Stone"
        override var avatar: String? =
            "https://www.onthisday.com/images/people/emma-stone-medium.jpg"
    }

    val user2 = object : Author {
        override var id = 1L.toString()
        override var name = "Matthew McConaughey"
        override var avatar: String? =
            "https://m.media-amazon.com/images/M/MV5BMTg0MDc3ODUwOV5BMl5BanBnXkFtZTcwMTk2NjY4Nw@@._V1_UX214_CR0,0,214,317_AL_.jpg"
    }

    val user3 = object : Author {
        override var id = 2L.toString()
        override var name = "Edward Norton"
        override var avatar: String? = ""
    }

    var nextId = 0L
        get() = field++

    var dialogId = 0L
        get() = field++

    var previousDate = Date()
        get() {
            val currentDate = field
            field = Date(field.time - Random.nextLong(44_000_000))
            return currentDate
        }

    var nextDate = Date()
        get() {
            val currentDate = field
            field = Date(field.time + Random.nextLong(44_000_000))
            return currentDate
        }

    val messagesList = mutableListOf<Message>()

    fun generateMessage(oldMessages: Boolean, messageText: String): Message {
        return if (Random.nextBoolean())
            generateTextMessage(oldMessages, messageText) else
            generateImageMessage(oldMessages, messageText)
    }

    private fun generateTextMessage(oldMessages: Boolean, messageText: String): TextMessage {
        return object : TextMessage {
            override var id = nextId.toString()

            private val user = when (Random.nextInt(3)) {
                0 -> user1
                1 -> user2
                2 -> user3
                else -> user3
            }

            override var date = if (oldMessages) previousDate else nextDate

            private val repliedOn = if (Random.nextInt(4) != 0)
                null
            else {
                if (messagesList.isEmpty())
                    null
                else
                    messagesList[Random.nextInt(messagesList.size)]
            }

            override var text = messageText

            override var author = user

            override var repliedMessage = repliedOn

            override val status: Message.Status
                get() {
                    val rand = Random.nextInt(10)
                    return when {
                        rand < 4 -> Message.Status.NONE
                        rand in 4..5 -> Message.Status.READ
                        else -> Message.Status.SENT
                    }
                }
        }.also { messagesList.add(it) }
    }

    fun generateImageMessage(oldMessages: Boolean, messageText: String): ImageMessage {
        return object : ImageMessage {
            override var id = nextId.toString()

            private val user = when (Random.nextInt(3)) {
                0 -> user1
                1 -> user2
                2 -> user3
                else -> user3
            }

            override var date = if (oldMessages) previousDate else nextDate

            private val url = when (Random.nextInt(3)) {
                0 -> "https://homepages.cae.wisc.edu/~ece533/images/frymire.png"
                1 -> "https://i.imgur.com/I8RXpNx.jpg"
                2 -> "https://homepages.cae.wisc.edu/~ece533/images/cat.png"
                else -> "https://homepages.cae.wisc.edu/~ece533/images/watch.png"
            }

            override var imageUrl = url

            override var text = messageText

            override var author = user

            override val status: Message.Status
                get() {
                    val rand = Random.nextInt(10)
                    return when {
                        rand < 4 -> Message.Status.NONE
                        rand in 4..5 -> Message.Status.READ
                        else -> Message.Status.SENT
                    }
                }
        }.also { messagesList.add(it) }
    }

    fun generateMessageText(words: Int = 20): String {
        val message = StringBuilder()
        repeat(words + 1) {
            message.append(wordsArray[Random.nextInt(wordsArray.size)])
            message.append(" ")
        }
        return message.toString().toLowerCase(Locale.ROOT).capitalize(Locale.ROOT)
    }

    fun generateDialogs(dialogsCount: Int = 20): List<Dialog> {
        val dialogs = mutableListOf<Dialog>()

        for (position in 0 until dialogsCount) {
            dialogs.add(position, object : Dialog {
                override var id = dialogId.toString()
                override var date = nextDate
                private val imageUrl = when (Random.nextInt(3)) {
                    0 -> "https://homepages.cae.wisc.edu/~ece533/images/frymire.png"
                    1 -> "https://i.imgur.com/I8RXpNx.jpg"
                    2 -> "https://homepages.cae.wisc.edu/~ece533/images/cat.png"
                    else -> "https://homepages.cae.wisc.edu/~ece533/images/watch.png"
                }
                private val messagesCount = Random.nextInt(3)
                private val message = generateTextMessage(true, generateMessageText())

                override var name = "Test chat #$id"

                override var photo: String? = imageUrl

                override var authors = listOf(user1, user2, user3)

                override var lastMessage = message as Message

                override var unreadMessagesCount = messagesCount
            })
        }

        return dialogs.toList()
    }

    private val wordsArray =
        "One evening in January 1842, Virginia showed the first signs of consumption, now known as tuberculosis, while singing and playing the piano, which Poe described as breaking a blood vessel in her throat. She only partially recovered, and Poe began to drink more heavily under the stress of her illness. He left Graham's and attempted to find a new position, for a time angling for a government post. He returned to New York where he worked briefly at the Evening Mirror before becoming editor of the Broadway Journal, and later its owner. There he alienated himself from other writers by publicly accusing Henry Wadsworth Longfellow of plagiarism, though Longfellow never responded. On January 29, 1845, his poem \"The Raven\" appeared in the Evening Mirror and became a popular sensation. It made Poe a household name almost instantly, though he was paid only \$9 for its publication. It was concurrently published in The American Review: A Whig Journal under the pseudonym \"Quarles\".".split(
            ' '
        )
}