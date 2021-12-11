package io.ktln.lib.telegram

public final class PollAnswerData(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val pollId: String,
        public val user: User.Data,
        public val optionId: List<Int>
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): PollAnswerData {
            return PollAnswerData(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): PollAnswerData {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
