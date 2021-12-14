package io.ktln.lib.telegram.enums

public final enum class BotCommandScopeType(
    public final val value: String
) {
    AllChatAdministrators("all_chat_administrators"),
    AllGroupChats("all_group_chats"),
    AllPrivateChats("all_private_chats"),
    Chat("chat"),
    ChatAdministrators("chat_administrators"),
    ChatMember("chat_member"),
    Default("default")
}
