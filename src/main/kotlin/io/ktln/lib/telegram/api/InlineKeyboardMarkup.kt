package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineKeyboardMarkup(
    public final val inlineKeyboard: List<List<InlineKeyboardButton>>
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineKeyboardMarkup {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineKeyboardMarkup {
            return dataMap.toDataClass()
        }
    }
}
