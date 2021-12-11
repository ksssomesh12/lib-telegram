package io.ktln.lib.telegram

public final class ReplyKeyboardMarkup(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val keyboard: List<List<KeyboardButton.Data>>,
        public val resizeKeyboard: Boolean? = null,
        public val oneTimeKeyboard: Boolean? = null,
        public val inputFieldPlaceholder: String? = null,
        public val selective: Boolean? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ReplyKeyboardMarkup {
            return ReplyKeyboardMarkup(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ReplyKeyboardMarkup {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
