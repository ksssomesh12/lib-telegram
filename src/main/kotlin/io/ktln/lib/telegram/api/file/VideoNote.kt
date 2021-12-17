package io.ktln.lib.telegram.api.file

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.PhotoSize
import io.ktln.lib.telegram.internal.toDataClass

public final data class VideoNote(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val length: Int,
    public final val duration: Int,
    public final val thumb: PhotoSize? = null,
    public final val fileSize: Int? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): VideoNote {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): VideoNote {
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
