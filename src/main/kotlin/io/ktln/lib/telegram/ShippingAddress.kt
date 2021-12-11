package io.ktln.lib.telegram

public final class ShippingAddress(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val countryCode: String,
        public val state: String,
        public val city: String,
        public val streetLine1: String,
        public val streetLine2: String,
        public val postCode: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ShippingAddress {
            return ShippingAddress(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ShippingAddress {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
