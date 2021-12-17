package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class SuccessfulPayment(
    public final val currency: String,
    public final val totalAmount: Int,
    public final val invoicePayload: String,
    public final val shippingOptionId: String? = null,
    public final val orderInfo: OrderInfo? = null,
    public final val telegramPaymentChargeId: String,
    public final val providerPaymentChargeId: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): SuccessfulPayment {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): SuccessfulPayment {
            return dataMap.toDataClass()
        }
    }
}
