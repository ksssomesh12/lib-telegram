package io.ktln.lib.telegram

public final class Audio(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val fileId: String,
        public val fileUniqueId: String,
        public val duration: Int,
        public val performer: String? = null,
        public val title: String? = null,
        public val fileName: String? = null,
        public val mimeType: String? = null,
        public val fileSize: Int? = null,
        public val thumb: PhotoSize.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Audio {
            return Audio(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Audio {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
