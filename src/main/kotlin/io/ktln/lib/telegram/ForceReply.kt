package io.ktln.lib.telegram

public final class ForceReply(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val forceReply: Boolean,
        public val inputFieldPlaceholder: String? = null,
        public val selective: Boolean? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ForceReply {
            return ForceReply(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ForceReply {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
