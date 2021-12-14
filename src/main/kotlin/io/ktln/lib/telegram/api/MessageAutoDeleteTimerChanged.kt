package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class MessageAutoDeleteTimerChanged(
    public final val messageAutoDeleteTime: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): MessageAutoDeleteTimerChanged {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): MessageAutoDeleteTimerChanged {
            return dataMap.toDataClass()
        }
    }
}
