package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ShippingOption(
    public final val id: String,
    public final val title: String,
    public final val prices: List<LabeledPrice>
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ShippingOption {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ShippingOption {
            return dataMap.toDataClass()
        }
    }
}
