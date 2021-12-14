package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.file.File
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatPhoto(
    public final val smallFileId: String,
    public final val smallFileUniqueId: String,
    public final val bigFileId: String,
    public final val bigFileUniqueId: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatPhoto {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatPhoto {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun getSmallFile(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): File {
        return bot.getFile(
            fileId = smallFileId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun getBigFile(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): File {
        return bot.getFile(
            fileId = bigFileId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
