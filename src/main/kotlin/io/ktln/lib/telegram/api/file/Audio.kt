package io.ktln.lib.telegram.api.file

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.PhotoSize
import io.ktln.lib.telegram.internal.toDataClass

public final data class Audio(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val duration: Int,
    public final val performer: String? = null,
    public final val title: String? = null,
    public final val fileName: String? = null,
    public final val mimeType: String? = null,
    public final val fileSize: Int? = null,
    public final val thumb: PhotoSize? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Audio {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Audio {
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
