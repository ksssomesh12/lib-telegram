package io.ktln.lib.telegram

public final class InputMediaPhoto(
    private val bot: Bot,
    private val data: Data
): InputMedia() {
    public final data class Data(
        public val type: String,
        public val media: String,
        public val caption: String? = null,
        public val parseMode: ParseMode? = null,
        public val captionEntities: List<MessageEntity.Data>? = null
    ): InputMedia.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): InputMediaPhoto {
            return InputMediaPhoto(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): InputMediaPhoto {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
