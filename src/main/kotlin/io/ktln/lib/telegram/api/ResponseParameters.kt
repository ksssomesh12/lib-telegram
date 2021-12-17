package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ResponseParameters(
    public final val migrateToChatId: Int? = null,
    public final val retryAfter: Int? = null
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ResponseParameters {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ResponseParameters {
            return dataMap.toDataClass()
        }
    }
}
