package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultVenue(
    public final val type: String,
    public final val id: String,
    public final val latitude: Float,
    public final val longitude: Float,
    public final val title: String,
    public final val address: String,
    public final val foursquareId: String? = null,
    public final val foursquareType: String? = null,
    public final val googlePlaceId: String? = null,
    public final val googlePlaceType: String? = null,
    public final val replyMarkup: InlineKeyboardMarkup? = null,
    public final val inputMessageContent: InputMessageContent? = null,
    public final val thumbUrl: String? = null,
    public final val thumbWidth: Int? = null,
    public final val thumbHeight: Int? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultVenue {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultVenue {
            return dataMap.toDataClass()
        }
    }
}
