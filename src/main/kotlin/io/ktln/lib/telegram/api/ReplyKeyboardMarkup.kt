package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ReplyKeyboardMarkup(
    public final val keyboard: List<List<KeyboardButton>>,
    public final val resizeKeyboard: Boolean? = null,
    public final val oneTimeKeyboard: Boolean? = null,
    public final val inputFieldPlaceholder: String? = null,
    public final val selective: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ReplyKeyboardMarkup {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ReplyKeyboardMarkup {
            return dataMap.toDataClass()
        }
    }
}
