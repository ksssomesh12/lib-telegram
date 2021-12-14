package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class LoginUrl(
    public final val url: String,
    public final val forwardText: String? = null,
    public final val botUsername: String? = null,
    public final val requestWriteAccess: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): LoginUrl {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): LoginUrl {
            return dataMap.toDataClass()
        }
    }
}
