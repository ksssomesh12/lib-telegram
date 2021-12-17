package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class LabeledPrice(
    public final val label: String,
    public final val amount: Int
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): LabeledPrice {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): LabeledPrice {
            return dataMap.toDataClass()
        }
    }
}
