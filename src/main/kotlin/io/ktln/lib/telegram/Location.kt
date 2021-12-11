package io.ktln.lib.telegram

public final class Location(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val longitude: Float,
        public val latitude: Float,
        public val horizontalAccuracy: Float? = null,
        public val livePeriod: Int? = null,
        public val heading: Int? = null,
        public val proximityAlertRadius: Int? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Location {
            return Location(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Location {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
