package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class VoiceChatEnded(
    public final val duration: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): VoiceChatEnded {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): VoiceChatEnded {
            return dataMap.toDataClass()
        }
    }
}
