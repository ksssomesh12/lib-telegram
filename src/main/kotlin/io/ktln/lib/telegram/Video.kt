package io.ktln.lib.telegram

public final class Video(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val fileId: String,
        public val fileUniqueId: String,
        public val width: Int,
        public val height: Int,
        public val duration: Int,
        public val thumb: PhotoSize.Data? = null,
        public val fileName: String? = null,
        public val mimeType: String? = null,
        public val fileSize: Int? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Video {
            return Video(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Video {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
