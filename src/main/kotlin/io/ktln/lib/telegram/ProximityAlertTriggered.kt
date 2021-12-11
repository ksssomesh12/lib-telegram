package io.ktln.lib.telegram

public final class ProximityAlertTriggered(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val traveler: User.Data,
        public val watcher: User.Data,
        public val distance: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ProximityAlertTriggered {
            return ProximityAlertTriggered(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ProximityAlertTriggered {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
