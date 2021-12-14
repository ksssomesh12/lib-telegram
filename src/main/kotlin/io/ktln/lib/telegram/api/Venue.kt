package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class Venue(
    public final val location: Location,
    public final val title: String,
    public final val address: String,
    public final val foursquareId: String? = null,
    public final val foursquareType: String? = null,
    public final val googlePlaceId: String? = null,
    public final val googlePlaceType: String? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Venue {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Venue {
            return dataMap.toDataClass()
        }
    }
}
