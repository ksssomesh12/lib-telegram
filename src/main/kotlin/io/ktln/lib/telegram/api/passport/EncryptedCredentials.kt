package io.ktln.lib.telegram.api.passport

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class EncryptedCredentials(
    public final val data: String,
    public final val hash: String,
    public final val secret: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): EncryptedCredentials {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): EncryptedCredentials {
            return dataMap.toDataClass()
        }
    }
}
