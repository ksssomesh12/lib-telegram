package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultGame(
    public final val type: String,
    public final val id: String,
    public final val gameShortName: String,
    public final val replyMarkup: InlineKeyboardMarkup? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultGame {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultGame {
            return dataMap.toDataClass()
        }
    }
}
