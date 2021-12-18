package io.ktln.lib.telegram.api.inline.content

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputContactMessageContent(
    public final val phoneNumber: String,
    public final val firstName: String,
    public final val lastName: String? = null,
    public final val vcard: String? = null
): InputMessageContent() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputContactMessageContent {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputContactMessageContent {
            return dataMap.toDataClass()
        }
    }
}
