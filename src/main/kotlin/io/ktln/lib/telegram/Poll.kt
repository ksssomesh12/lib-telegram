package io.ktln.lib.telegram

public final class Poll(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val id: String,
        public val question: String,
        public val options: List<PollOption.Data>,
        public val totalVoterCount: Int,
        public val isClosed: Boolean,
        public val isAnonymous: Boolean,
        public val type: String,
        public val allowsMultipleAnswers: Boolean,
        public val correctOptionId: Int? = null,
        public val explanation: String? = null,
        public val explanationEntities: List<MessageEntity.Data>? = null,
        public val openPeriod: Int? = null,
        public val closeDate: Int? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Poll {
            return Poll(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Poll {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
