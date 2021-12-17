package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class KeyboardButton(
    public final val text: String,
    public final val requestContact: Boolean? = null,
    public final val requestLocation: Boolean? = null,
    public final val requestPoll: KeyboardButtonPollType? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): KeyboardButton {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): KeyboardButton {
            return dataMap.toDataClass()
        }
    }
}
