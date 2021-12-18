package io.ktln.lib.telegram.api.inline.result

import io.ktln.lib.telegram.api.InlineKeyboardMarkup
import io.ktln.lib.telegram.api.inline.content.InputMessageContent
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQueryResultArticle(
    public final val type: String,
    public final val id: String,
    public final val title: String,
    public final val inputMessageContent: InputMessageContent,
    public final val replyMarkup: InlineKeyboardMarkup? = null,
    public final val url: String? = null,
    public final val hideUrl: Boolean? = null,
    public final val description: String? = null,
    public final val thumbUrl: String? = null,
    public final val thumbWidth: Int? = null,
    public final val thumbHeight: Int? = null
): InlineQueryResult() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQueryResultArticle {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQueryResultArticle {
            return dataMap.toDataClass()
        }
    }
}
