package io.ktln.lib.telegram.api.inline.content

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputVenueMessageContent(
    public final val latitude: Float,
    public final val longitude: Float,
    public final val title: String,
    public final val address: String,
    public final val foursquareId: String? = null,
    public final val foursquareType: String? = null,
    public final val googlePlaceId: String? = null,
    public final val googlePlaceType: String? = null
): InputMessageContent() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputVenueMessageContent {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputVenueMessageContent {
            return dataMap.toDataClass()
        }
    }
}
