package io.ktln.lib.telegram.api.passport

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class PassportData(
    public final val data: List<EncryptedPassportElement>,
    public final val credentials: EncryptedCredentials
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): PassportData {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): PassportData {
            return dataMap.toDataClass()
        }
    }
}
