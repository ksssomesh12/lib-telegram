package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatMemberOwner(
    public final val status: String,
    public final val user: User,
    public final val isAnonymous: Boolean,
    public final val customTitle: String? = null
): ChatMember() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatMemberOwner {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatMemberOwner {
            return dataMap.toDataClass()
        }
    }
}
