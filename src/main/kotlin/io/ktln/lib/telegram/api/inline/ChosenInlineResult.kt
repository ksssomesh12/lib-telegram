package io.ktln.lib.telegram.api.inline

import io.ktln.lib.telegram.api.Location
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChosenInlineResult(
    public final val resultId: String,
    public final val from: User,
    public final val location: Location? = null,
    public final val inlineMessageId: String? = null,
    public final val query: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChosenInlineResult {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChosenInlineResult {
            return dataMap.toDataClass()
        }
    }
}
