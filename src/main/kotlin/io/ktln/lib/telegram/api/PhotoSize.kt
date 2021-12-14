package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.file.File
import io.ktln.lib.telegram.internal.toDataClass

public final data class PhotoSize(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val width: Int,
    public final val height: Int,
    public final val fileSize: Int? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): PhotoSize {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): PhotoSize {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun getFile(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): File {
        return bot.getFile(
            fileId = fileId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
