package io.ktln.lib.telegram.api.inline.result.cached

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.api.inline.result.InlineQueryResult
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultCachedSticker(
    public final val type: String,
    public final val id: String,
    public final val stickerFileId: String,
    public final val replyMarkup: InlineKeyboardMarkup? = null,
    public final val inputMessageContent: InputMessageContent? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultCachedSticker {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultCachedSticker {
            return dataMap.toDataClass()
        }
    }
}
