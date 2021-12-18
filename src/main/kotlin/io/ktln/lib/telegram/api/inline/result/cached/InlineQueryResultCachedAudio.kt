package io.ktln.lib.telegram.api.inline.result.cached

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.api.inline.result.InlineQueryResult
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultCachedAudio(
    public final val type: String,
    public final val id: String,
    public final val audioFileId: String,
    public final val caption: String? = null,
    public final val parseMode: ParseMode? = null,
    public final val captionEntities: List<MessageEntity>? = null,
    public final val replyMarkup: InlineKeyboardMarkup? = null,
    public final val inputMessageContent: InputMessageContent? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultCachedAudio {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultCachedAudio {
            return dataMap.toDataClass()
        }
    }
}
