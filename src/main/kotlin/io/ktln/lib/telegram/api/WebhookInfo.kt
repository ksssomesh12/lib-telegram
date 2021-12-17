package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class WebhookInfo(
    public final val url: String,
    public final val hasCustomCertificate: Boolean,
    public final val pendingUpdateCount: Int,
    public final val ipAddress: String? = null,
    public final val lastErrorDate: Int? = null,
    public final val lastErrorMessage: String? = null,
    public final val maxConnections: Int? = null,
    public final val allowedUpdates: List<String>? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): WebhookInfo {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): WebhookInfo {
            return dataMap.toDataClass()
        }
    }
}
