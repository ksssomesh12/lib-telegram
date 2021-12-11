package io.ktln.lib.telegram

public final class Dice(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val emoji: String,
        public val value: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Dice {
            return Dice(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Dice {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
