package io.ktln.lib.telegram.api.sticker

import io.ktln.lib.telegram.api.PhotoSize
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.file.File
import io.ktln.lib.telegram.internal.toDataClass

public final data class Sticker(
    public final val fileId: String,
    public final val fileUniqueId: String,
    public final val width: Int,
    public final val height: Int,
    public final val isAnimated: Boolean,
    public final val thumb: PhotoSize? = null,
    public final val emoji: String? = null,
    public final val setName: String? = null,
    public final val maskPosition: MaskPosition? = null,
    public final val fileSize: Int? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Sticker {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Sticker {
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
