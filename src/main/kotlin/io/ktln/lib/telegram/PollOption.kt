package io.ktln.lib.telegram

public final class PollOption(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val text: String,
        public val voterCount: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): PollOption {
            return PollOption(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): PollOption {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
