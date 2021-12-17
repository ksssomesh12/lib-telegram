package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ProximityAlertTriggered(
    public final val traveler: User,
    public final val watcher: User,
    public final val distance: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ProximityAlertTriggered {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ProximityAlertTriggered {
            return dataMap.toDataClass()
        }
    }
}
