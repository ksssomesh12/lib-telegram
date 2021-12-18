package io.ktln.lib.telegram.api.inline.content

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputLocationMessageContent(
    public final val latitude: Float,
    public final val longitude: Float,
    public final val horizontalAccuracy: Float? = null,
    public final val livePeriod: Int? = null,
    public final val heading: Int? = null,
    public final val proximityAlertRadius: Int? = null
): InputMessageContent() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputLocationMessageContent {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputLocationMessageContent {
            return dataMap.toDataClass()
        }
    }
}
