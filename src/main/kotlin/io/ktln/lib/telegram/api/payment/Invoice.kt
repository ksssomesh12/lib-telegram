package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class Invoice(
    public final val title: String,
    public final val description: String,
    public final val startParameter: String,
    public final val currency: String,
    public final val totalAmount: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Invoice {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Invoice {
            return dataMap.toDataClass()
        }
    }
}
