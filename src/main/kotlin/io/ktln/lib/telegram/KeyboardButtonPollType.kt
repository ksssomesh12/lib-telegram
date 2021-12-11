package io.ktln.lib.telegram

public final class KeyboardButtonPollType(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val type: String? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): KeyboardButtonPollType {
            return KeyboardButtonPollType(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): KeyboardButtonPollType {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
