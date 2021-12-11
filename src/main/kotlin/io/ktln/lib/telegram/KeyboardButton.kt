package io.ktln.lib.telegram

public final class KeyboardButton(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val text: String,
        public val requestContact: Boolean? = null,
        public val requestLocation: Boolean? = null,
        public val requestPoll: KeyboardButtonPollType.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): KeyboardButton {
            return KeyboardButton(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): KeyboardButton {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
