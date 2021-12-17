package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatMemberBanned(
    public final val status: String,
    public final val user: User,
    public final val untilDate: Int
): ChatMember() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatMemberBanned {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatMemberBanned {
            return dataMap.toDataClass()
        }
    }
}
