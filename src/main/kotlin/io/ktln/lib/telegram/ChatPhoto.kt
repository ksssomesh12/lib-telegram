package io.ktln.lib.telegram

public final class ChatPhoto(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val smallFileId: String,
        public val smallFileUniqueId: String,
        public val bigFileId: String,
        public val bigFileUniqueId: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatPhoto {
            return ChatPhoto(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatPhoto {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
