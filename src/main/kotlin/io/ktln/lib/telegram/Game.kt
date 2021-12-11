package io.ktln.lib.telegram

public final class Game(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val title: String,
        public val description: String,
        public val photo: List<PhotoSize.Data>,
        public val text: String? = null,
        public val textEntities: List<MessageEntity.Data>? = null,
        public val animation: Animation.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Game {
            return Game(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Game {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
