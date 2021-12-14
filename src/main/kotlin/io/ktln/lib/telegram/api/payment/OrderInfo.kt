package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class OrderInfo(
    public final val name: String? = null,
    public final val phoneNumber: String? = null,
    public final val email: String? = null,
    public final val shippingAddress: ShippingAddress? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): OrderInfo {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): OrderInfo {
            return dataMap.toDataClass()
        }
    }
}
