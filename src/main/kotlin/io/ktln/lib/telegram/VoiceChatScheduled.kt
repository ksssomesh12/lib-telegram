package io.ktln.lib.telegram

public final class VoiceChatScheduled(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val startDate: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): VoiceChatScheduled {
            return VoiceChatScheduled(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): VoiceChatScheduled {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
