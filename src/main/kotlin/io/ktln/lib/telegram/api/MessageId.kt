package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class MessageId(
    public final val messageId: Int
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): MessageId {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): MessageId {
            return dataMap.toDataClass()
        }
    }
}
