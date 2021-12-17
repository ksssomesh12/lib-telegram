package io.ktln.lib.telegram.api.file

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class File(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val fileSize: Int? = null,
    public final val filePath: String? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): File {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): File {
            return dataMap.toDataClass()
        }
    }
}
