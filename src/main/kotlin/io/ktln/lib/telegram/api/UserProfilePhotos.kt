package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class UserProfilePhotos(
    public final val totalCount: Int,
    public final val photos: List<List<PhotoSize>>
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): UserProfilePhotos {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): UserProfilePhotos {
            return dataMap.toDataClass()
        }
    }
}
