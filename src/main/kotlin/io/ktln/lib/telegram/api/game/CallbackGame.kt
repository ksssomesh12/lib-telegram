package io.ktln.lib.telegram.api.game

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class CallbackGame(
    public final val nothing: Nothing
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): CallbackGame {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): CallbackGame {
            return dataMap.toDataClass()
        }
    }
}
