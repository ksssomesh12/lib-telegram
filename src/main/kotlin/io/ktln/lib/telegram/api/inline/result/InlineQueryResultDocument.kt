package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultDocument(
    public final val type: String,
    public final val id: String,
    public final val title: String,
    public final val caption: String? = null,
    public final val parseMode: ParseMode? = null,
    public final val captionEntities: List<MessageEntity>? = null,
    public final val documentUrl: String,
    public final val mimeType: String,
    public final val description: String? = null,
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
    ): InlineQueryResultDocument {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultDocument {
            return dataMap.toDataClass()
        }
    }
}
