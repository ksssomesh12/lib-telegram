package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultVoice(
    public final val type: String,
    public final val id: String,
    public final val voiceUrl: String,
    public final val title: String,
    public final val caption: String? = null,
    public final val parseMode: ParseMode? = null,
    public final val captionEntities: List<MessageEntity>? = null,
    public final val voiceDuration: Int? = null,
    public final val replyMarkup: InlineKeyboardMarkup? = null,
    public final val inputMessageContent: InputMessageContent? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultVoice {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultVoice {
            return dataMap.toDataClass()
        }
    }
}
