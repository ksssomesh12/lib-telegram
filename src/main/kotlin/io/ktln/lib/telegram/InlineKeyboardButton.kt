package io.ktln.lib.telegram

public final class InlineKeyboardButton(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val text: String,
        public val url: String? = null,
        public val loginUrl: LoginUrl.Data? = null,
        public val callbackData: String? = null,
        public val switchInlineQuery: String? = null,
        public val switchInlineQueryCurrentChat: String? = null,
        public val callbackGame: CallbackGame.Data? = null,
        public val pay: Boolean? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): InlineKeyboardButton {
            return InlineKeyboardButton(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): InlineKeyboardButton {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
