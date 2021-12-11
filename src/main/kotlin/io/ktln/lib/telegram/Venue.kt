package io.ktln.lib.telegram

public final class Venue(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val location: Location.Data,
        public val title: String,
        public val address: String,
        public val foursquareId: String? = null,
        public val foursquareType: String? = null,
        public val googlePlaceId: String? = null,
        public val googlePlaceType: String? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Venue {
            return Venue(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Venue {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
