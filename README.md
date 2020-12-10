# YaChat

YaChat is Android UI library for quickly chats implementation. Library components have many customization options.

## Demo Application
In [app](https://github.com/AppVelox/yachat/tree/master/app) directory there is a simple example of usage library. You can also see [application](https://github.com/AppVelox/YaYaChat) with Firebase Services using. 

## Download
1) Authenticate to GitHub Packages. For more information, see ["Authenticating to GitHub Packages"](https://docs.github.com/en/free-pro-team@latest/packages/guides/configuring-gradle-for-use-with-github-packages#authenticating-to-github-packages)
```groovy
def githubProperties = new Properties()
githubProperties.load(new FileInputStream(rootProject.file("github.properties")))

repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/appvelox/yachat")

        credentials {
            username = githubProperties['gpr.usr'] ?: System.getenv("GPR_USER")
            password = githubProperties['gpr.key'] ?: System.getenv("GPR_API_KEY")
        }
    }
}
```

2) Add dependencies in your app's `build.gradle` file
```groovy
dependencies{
    implementation 'joda-time:joda-time:2.10.2'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'ru.appvelox:chat:1.0.2'
}
```

## Usage
YaChat has the following components:
- ChatView
- DialogView
- ChatInput

### ChatView
ChatView is a component for displaying messages in conversation. For using this component you need:
1) __Add the view to your layout XML__
```xml
<ru.appvelox.chat.ChatView
    android:id="@+id/chatView"
    android:layout_width="0dp"
    android:layout_height="0dp"/>
```
2) __Implement user model__

You must implement `Author` interface. 
``` kotlin
@Parcelize
data class ChatAuthor(
    override var id: String = "",
    override var name: String = "",
    override var avatar: String? = ""
) : Author, Parcelable
```
3) __Implement message model__

You must implement `Message` interface. Library has two types of message interfaces: `TextMessage` for text messages and `ImageMessage` for image messages.
```kotlin
@Parcelize
class ChatImageMessage(
    override var id: String,
    override var text: String,
    override var author: @RawValue Author,
    override var date: Date,
    override var status: Message.Status,
    override var imageUrl: String = ""
) : ChatMessage(id, text, author, date, status), ImageMessage, Parcelable
```
4) __Set current user ID in your Activity__

To distinguish between incoming and outgoing messages.
```kotlin
chatView.setCurrentUserId(MessageGenerator.user1.id)
```
5) __Add list of messages or single message__

```kotlin
chatView.addMessages(messages)

chatView.addMessage(message)
```
6) __(Optional) Customize ChatView__

ChatView has many appearance options.
```kotlin
chatView.setAuthorTextColor(textColor1)
chatView.setMessageTextColor(textColor2)
chatView.setReplyAuthorTextColor(textColor3)
chatView.setReplyMessageTextColor(textColor4)
```
7) __(Optional) Set listeners__

```kotlin
chatInput.setOnSendButtonClickListener(object : ChatInput.OnSendButtonClickListener {
    override fun onClick(input: CharSequence?) {
        chatView.addMessage(MessageGenerator.generateMessage(false, input.toString()))
        chatInput.setText(MessageGenerator.generateMessageText())
    }
})

chatView.setOnMessageSelectedListener(object : ChatView.OnMessageSelectedListener {
    override fun onSelected(selected: Boolean) {
        if (selected) {
            showMenuIcons()
        } else {
            hideMenuIcons()
        }
    }
})
```

### ChatInput
ChatInput is a component for typing and sending messages. For using this component you need:
1) __Add the view to your layout XML__
```xml
<ru.appvelox.chat.ChatInput
    android:id="@+id/chatInput"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```
2) __Set listeners for buttons__
```kotlin
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
```
3) __(Optional) Set max lines__

You can set the ChatInput height (default heigth is 6 lines)
```kotlin
chatInput.setMaxLines(4)
```

### DialogView
DialogView is a component for displaying user dialogs. For using this component you need:
1) __Add the view to your layout XML__
```xml
<ru.appvelox.dialog.DialogView
    android:id="@+id/dialogView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
2) __Implement dialog model__

You must implement `Dialog` interface. You also need `Author` and `Message` interfaces.
``` kotlin
@Parcelize
data class ChatDialog(
    override var id: String = "",
    override var name: String = "",
    override var date: Date = Date(),
    override var authors: @RawValue List<Author> = listOf<ChatAuthor>(),
    override var unreadMessagesCount: Int = 0,
    override var lastMessage: @RawValue Message = ChatMessage(),
    override var photo: String? = ""
) : Dialog, Parcelable
```
3) __Add list of dialogs__

```kotlin
dialogView.addDialogs(dialogs)
```

## Screenshots
DialogView             |  ChatView and ChatInput
:-------------------------:|:-------------------------:
![](https://i.imgur.com/gH9AQM6.png)  |  ![](https://i.imgur.com/PVnCRTz.png)
