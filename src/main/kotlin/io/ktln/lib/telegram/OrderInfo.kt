package io.ktln.lib.telegram

public final class OrderInfo(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val name: String? = null,
        public val phoneNumber: String? = null,
        public val email: String? = null,
        public val shippingAddress: ShippingAddress.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): OrderInfo {
            return OrderInfo(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): OrderInfo {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}