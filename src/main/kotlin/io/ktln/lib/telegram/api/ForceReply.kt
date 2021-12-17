package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ForceReply(
    public final val forceReply: Boolean,
    public final val inputFieldPlaceholder: String? = null,
    public final val selective: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ForceReply {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ForceReply {
            return dataMap.toDataClass()
        }
    }
}
