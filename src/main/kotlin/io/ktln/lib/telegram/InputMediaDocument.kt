package io.ktln.lib.telegram

public final class InputMediaDocument(
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
        public val disableContentTypeDetection: Boolean? = null
    ): InputMedia.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): InputMediaDocument {
            return InputMediaDocument(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): InputMediaDocument {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
