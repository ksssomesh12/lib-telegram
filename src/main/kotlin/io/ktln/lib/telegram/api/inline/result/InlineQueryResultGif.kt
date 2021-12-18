package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultGif(
    public final val type: String,
    public final val id: String,
    public final val gifUrl: String,
    public final val gifWidth: Int? = null,
    public final val gifHeight: Int? = null,
    public final val gifDuration: Int? = null,
    public final val thumbUrl: String,
    public final val thumbMimeType: String? = null,
    public final val title: String? = null,
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
    ): InlineQueryResultGif {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultGif {
            return dataMap.toDataClass()
        }
    }
}
