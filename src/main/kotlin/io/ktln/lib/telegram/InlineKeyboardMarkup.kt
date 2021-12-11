package io.ktln.lib.telegram

public final class InlineKeyboardMarkup(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val inlineKeyboard: List<List<InlineKeyboardButton.Data>>
    )
    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): InlineKeyboardMarkup {
            return InlineKeyboardMarkup(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): InlineKeyboardMarkup {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
