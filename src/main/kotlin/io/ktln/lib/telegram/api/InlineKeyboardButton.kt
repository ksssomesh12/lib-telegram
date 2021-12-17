package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.game.CallbackGame
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineKeyboardButton(
    public final val text: String,
    public final val url: String? = null,
    public final val loginUrl: LoginUrl? = null,
    public final val callbackData: String? = null,
    public final val switchInlineQuery: String? = null,
    public final val switchInlineQueryCurrentChat: String? = null,
    public final val callbackGame: CallbackGame? = null,
    public final val pay: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineKeyboardButton {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineKeyboardButton {
            return dataMap.toDataClass()
        }
    }
}
