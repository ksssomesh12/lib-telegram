package io.ktln.lib.telegram.api.sticker

import io.ktln.lib.telegram.api.PhotoSize
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class StickerSet(
    public final val name: String,
    public final val title: String,
    public final val isAnimated: Boolean,
    public final val containsMasks: Boolean,
    public final val stickers: List<Sticker>,
    public final val thumb: PhotoSize? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): StickerSet {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): StickerSet {
            return dataMap.toDataClass()
        }
    }
}
