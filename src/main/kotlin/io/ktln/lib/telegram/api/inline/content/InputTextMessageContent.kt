package io.ktln.lib.telegram.api.inline.content

import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputTextMessageContent(
    public final val messageText: String,
    public final val parseMode: ParseMode? = null,
    public final val entities: List<MessageEntity>? = null,
    public final val disableWebPagePreview: Boolean? = null
): InputMessageContent() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputTextMessageContent {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputTextMessageContent {
            return dataMap.toDataClass()
        }
    }
}
