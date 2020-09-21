package ru.appvelox.myapplication

import ru.appvelox.chat.model.Author
import ru.appvelox.chat.model.ImageMessage
import ru.appvelox.chat.model.Message
import ru.appvelox.chat.model.TextMessage
import java.lang.StringBuilder
import java.util.*
import kotlin.random.Random

object MessageGenerator {

    val user1 = object : Author {
        override fun getName() = "Emma Stone"

        override fun getId() = 0L

        override fun getAvatar(): String {
            return "https://www.onthisday.com/images/people/emma-stone-medium.jpg"
        }
    }

    val user2 = object : Author {
        override fun getName() = "Matthew McConaughey"

        override fun getId() = 1L

        override fun getAvatar(): String {
            return "https://m.media-amazon.com/images/M/MV5BMTg0MDc3ODUwOV5BMl5BanBnXkFtZTcwMTk2NjY4Nw@@._V1_UX214_CR0,0,214,317_AL_.jpg"
        }
    }

    val user3 = object : Author {
        override fun getName() = "Edward Norton"

        override fun getId() = 2L

        override fun getAvatar(): String {
            return "https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fblogs-images.forbes.com%2Fdannyboice%2Ffiles%2F2014%2F07%2FEN-by-KB-e1404325314835.jpg"
        }
    }

    var nextId = 0L
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

    val messagesList = mutableListOf<TextMessage>()

    fun generateMessage(oldMessages: Boolean): Message {
        return if(Random.nextBoolean()) object : TextMessage {

            private val mId = nextId
            private val mUser = when (Random.nextInt(3)) {
                0 -> user1
                1 -> user2
                2 -> user3
                else -> user3
            }
            private val mMessageText = MessageGenerator.generateMessageText()
            private val mDate = if (oldMessages) previousDate else nextDate
            private val repliedOn = if (Random.nextInt(4) != 0)
                null
            else {
                if (messagesList.isEmpty())
                    null
                else
                    messagesList[Random.nextInt(messagesList.size)]
            }

            private val mIsRead = Random.nextBoolean()
            private val mIsSent = !mIsRead

            override fun getId(): Long {
                return mId
            }

            override fun getText(): String {
                return mMessageText
            }

            override fun getAuthor(): Author {
                return mUser
            }

            override fun getDate(): Date {
                return mDate
            }

            override fun getRepliedMessage(): TextMessage? {
                return repliedOn
            }

            override fun getStatus(): Message.Status {
                val rand = Random.nextInt(10)
                return if(rand < 4) Message.Status.NONE else if(rand in 4..5) Message.Status.READ else Message.Status.SENT
            }

        }.also { messagesList.add(it) }
        else object: ImageMessage{
            private val mId = nextId
            private val mUser = when (Random.nextInt(3)) {
                0 -> user1
                1 -> user2
                2 -> user3
                else -> user3
            }
            private val mMessageText = MessageGenerator.generateMessageText()
            private val mDate = if (oldMessages) previousDate else nextDate

            private val mIsRead = Random.nextBoolean()
            private val mIsSent = !mIsRead
            private val mImageUrl = "https://divo-dacha.ru/wp-content/uploads/2016/06/piony-opisanie-istoriya-legendy-01.jpg"


            override fun getImageUrl(): String? {
                return mImageUrl
            }

            override fun getId(): Long {
                return mId
            }

            override fun getText(): String {
                return mMessageText
            }

            override fun getAuthor(): Author {
                return mUser
            }

            override fun getDate(): Date {
                return mDate
            }

            override fun getStatus(): Message.Status {
                val rand = Random.nextInt(10)
                return if(rand < 4) Message.Status.NONE else if(rand in 4..5) Message.Status.READ else Message.Status.SENT
            }
        }
    }

    fun generateMessage(oldMessages: Boolean, messageText: String): TextMessage {
        return object : TextMessage {

            private val mId = nextId
            private val mUser = when (Random.nextInt(3)) {
                0 -> user1
                1 -> user2
                2 -> user3
                else -> user3
            }
            private val mMessageText = messageText
            private val mDate = if (oldMessages) previousDate else nextDate
            private val repliedOn = if (Random.nextInt(4) != 0)
                null
            else {
                if (messagesList.isEmpty())
                    null
                else
                    messagesList[Random.nextInt(messagesList.size)]
            }

            private val mIsRead = Random.nextBoolean()
            private val mIsSent = !mIsRead

            override fun getId(): Long {
                return mId
            }

            override fun getText(): String {
                return mMessageText
            }

            override fun getAuthor(): Author {
                return mUser
            }

            override fun getDate(): Date {
                return mDate
            }

            override fun getRepliedMessage(): TextMessage? {
                return repliedOn
            }

            override fun getStatus(): Message.Status {
                val rand = Random.nextInt(10)
                return if(rand < 4) Message.Status.NONE else if(rand in 4..5) Message.Status.READ else Message.Status.SENT
            }
        }.also { messagesList.add(it) }
    }


    fun generateMessageText(words: Int = 20): String {
        val wordNumber = wordsArray.size
        val message = StringBuilder()
        repeat(words + 1) {
            message.append(wordsArray[Random.nextInt(wordNumber)])
            message.append(" ")

        }
        return message.toString().toLowerCase().capitalize()
    }

    val wordsArray =
        "One evening in January 1842, Virginia showed the first signs of consumption, now known as tuberculosis, while singing and playing the piano, which Poe described as breaking a blood vessel in her throat. She only partially recovered, and Poe began to drink more heavily under the stress of her illness. He left Graham's and attempted to find a new position, for a time angling for a government post. He returned to New York where he worked briefly at the Evening Mirror before becoming editor of the Broadway Journal, and later its owner. There he alienated himself from other writers by publicly accusing Henry Wadsworth Longfellow of plagiarism, though Longfellow never responded. On January 29, 1845, his poem \"The Raven\" appeared in the Evening Mirror and became a popular sensation. It made Poe a household name almost instantly, though he was paid only \$9 for its publication. It was concurrently published in The American Review: A Whig Journal under the pseudonym \"Quarles\".".split(
            ' '
        )
}