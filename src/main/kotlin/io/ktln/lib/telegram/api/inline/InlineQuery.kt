package io.ktln.lib.telegram.api.inline

import io.ktln.lib.telegram.api.Location
import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.User
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
}
