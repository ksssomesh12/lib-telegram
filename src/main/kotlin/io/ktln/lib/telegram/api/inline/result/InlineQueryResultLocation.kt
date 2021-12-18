package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultLocation(
    public final val type: String,
    public final val id: String,
    public final val latitude: Float,
    public final val longitude: Float,
    public final val title: String,
    public final val horizontalAccuracy: Float? = null,
    public final val livePeriod: Int? = null,
    public final val heading: Int? = null,
    public final val proximityAlertRadius: Int? = null,
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
    ): InlineQueryResultLocation {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultLocation {
            return dataMap.toDataClass()
        }
    }
}
