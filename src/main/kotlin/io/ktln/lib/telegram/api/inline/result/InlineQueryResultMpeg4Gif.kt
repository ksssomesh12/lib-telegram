package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultMpeg4Gif(
    public final val type: String,
    public final val id: String,
    public final val mpeg4Url: String,
    public final val mpeg4Width: Int? = null,
    public final val mpeg4Height: Int? = null,
    public final val mpeg4Duration: Int? = null,
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
    ): InlineQueryResultMpeg4Gif {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultMpeg4Gif {
            return dataMap.toDataClass()
        }
    }
}
