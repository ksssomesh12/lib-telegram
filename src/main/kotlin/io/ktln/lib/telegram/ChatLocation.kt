package io.ktln.lib.telegram

public final class ChatLocation(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val location: Location.Data,
        public val address: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatLocation {
            return ChatLocation(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatLocation {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
