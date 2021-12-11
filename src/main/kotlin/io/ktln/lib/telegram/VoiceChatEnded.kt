package io.ktln.lib.telegram

public final class VoiceChatEnded(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val duration: Int
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): VoiceChatEnded {
            return VoiceChatEnded(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): VoiceChatEnded {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
