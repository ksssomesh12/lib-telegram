package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatJoinRequest(
    public final val chat: Chat,
    public final val from: User,
    public final val date: Int,
    public final val bio: String? = null,
    public final val inviteLink: ChatInviteLink? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatJoinRequest {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatJoinRequest {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun approveChatJoinRequest(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.approveChatJoinRequest(
            chatId = chat.id,
            userId = from.id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun declineChatJoinRequest(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.declineChatJoinRequest(
            chatId = chat.id,
            userId = from.id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
