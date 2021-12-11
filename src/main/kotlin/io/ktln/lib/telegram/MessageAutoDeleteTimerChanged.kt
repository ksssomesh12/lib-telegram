package io.ktln.lib.telegram

public final class MessageAutoDeleteTimerChanged(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val messageAutoDeleteTime: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): MessageAutoDeleteTimerChanged {
            return MessageAutoDeleteTimerChanged(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): MessageAutoDeleteTimerChanged {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
