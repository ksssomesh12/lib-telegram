package io.ktln.lib.telegram

public final class BotCommand(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val command: String,
        public val description: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): BotCommand {
            return BotCommand(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): BotCommand {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
