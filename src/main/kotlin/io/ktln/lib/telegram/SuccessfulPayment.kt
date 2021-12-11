package io.ktln.lib.telegram

public final class SuccessfulPayment(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val currency: String,
        public val totalAmount: Int,
        public val invoicePayload: String,
        public val shippingOptionId: String?,
        public val orderInfo: OrderInfo.Data?,
        public val telegramPaymentChargeId: String,
        public val providerPaymentChargeId: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): SuccessfulPayment {
            return SuccessfulPayment(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): SuccessfulPayment {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
