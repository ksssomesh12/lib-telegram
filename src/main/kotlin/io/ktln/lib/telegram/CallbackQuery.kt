package io.ktln.lib.telegram

class CallbackQuery(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val id: String,
        public val from: User.Data,
        public val message: Message.Data? = null,
        public val inlineMessageId: String? = null,
        public val chatInstance: String,
        public val data: String? = null,
        public val gameShortName: String? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): CallbackQuery {
            return CallbackQuery(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): CallbackQuery {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
