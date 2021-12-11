package io.ktln.lib.telegram

public final class CallbackGame(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val noInfo: Nothing
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): CallbackGame {
            return CallbackGame(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): CallbackGame {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}