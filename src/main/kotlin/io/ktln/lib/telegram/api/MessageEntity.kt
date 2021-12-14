package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class MessageEntity(
    public final val type: String,
    public final val offset: Int,
    public final val length: Int,
    public final val url: String? = null,
    public final val user: User? = null,
    public final val language: String? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): MessageEntity {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): MessageEntity {
            return dataMap.toDataClass()
        }
    }
}
