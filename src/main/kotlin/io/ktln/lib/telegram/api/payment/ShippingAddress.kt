package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ShippingAddress(
    public final val countryCode: String,
    public final val state: String,
    public final val city: String,
    public final val streetLine1: String,
    public final val streetLine2: String,
    public final val postCode: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ShippingAddress {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ShippingAddress {
            return dataMap.toDataClass()
        }
    }
}
