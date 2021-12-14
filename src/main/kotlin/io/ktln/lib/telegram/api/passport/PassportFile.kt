package io.ktln.lib.telegram.api.passport

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class PassportFile(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val fileSize: Int,
    public final val fileDate: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): PassportFile {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): PassportFile {
            return dataMap.toDataClass()
        }
    }
}
