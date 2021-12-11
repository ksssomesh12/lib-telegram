package io.ktln.lib.telegram

public final class Sticker(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val fileId: String,
        public val fileUniqueId: String,
        public val width: Int,
        public val height: Int,
        public val isAnimated: Boolean,
        public val thumb: PhotoSize.Data? = null,
        public val emoji: String? = null,
        public val setName: String? = null,
        public val maskPosition: MaskPosition.Data? = null,
        public val fileSize: Int? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Sticker {
            return Sticker(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Sticker {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
