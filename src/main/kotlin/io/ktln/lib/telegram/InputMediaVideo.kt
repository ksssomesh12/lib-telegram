package io.ktln.lib.telegram

public final class InputMediaVideo(
    private val bot: Bot,
    private val data: Data
    ): InputMedia() {
    public final data class Data(
        public val type: String,
        public val media: String,
        public val thumb: InputFile.Data? = null,
        public val caption: String? = null,
        public val parseMode: ParseMode? = null,
        public val captionEntities: List<MessageEntity.Data>? = null,
        public val width: Int? = null,
        public val height: Int? = null,
        public val duration: Int? = null,
        public val supportsStreaming: Boolean? = null
    ): InputMedia.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): InputMediaVideo {
            return InputMediaVideo(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): InputMediaVideo {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
