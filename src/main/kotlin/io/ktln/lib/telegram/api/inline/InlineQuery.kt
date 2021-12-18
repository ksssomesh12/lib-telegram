package io.ktln.lib.telegram.api.inline

import io.ktln.lib.telegram.api.Location
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.api.inline.result.InlineQueryResult
import io.ktln.lib.telegram.internal.toDataClass

public final data class InlineQuery(
    public final val id: String,
    public final val from: User,
    public final val query: String,
    public final val offset: String,
    public final val chatType: String? = null,
    public final val location: Location? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InlineQuery {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InlineQuery {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun answerInlineQuery(
        results: List<InlineQueryResult>,
        cacheTime: Int = 300,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null,
        currentOffset: String? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        autoPagination: Boolean = false
    ): Boolean {
        if (autoPagination and (currentOffset is String)) throw Exception("ValueError !")
        return bot.answerInlineQuery(
            inlineQueryId = id,
            results = results,
            cacheTime = cacheTime,
            isPersonal = isPersonal,
            nextOffset = nextOffset,
            switchPmText = switchPmText,
            switchPmParameter = switchPmParameter,
            currentOffset = if (autoPagination) offset else currentOffset,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
