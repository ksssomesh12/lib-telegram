package io.ktln.lib.telegram

public final class MessageEntity(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val type: String,
        public val offset: Int,
        public val length: Int,
        public val url: String? = null,
        public val user: User.Data? = null,
        public val language: String? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): MessageEntity {
            return MessageEntity(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): MessageEntity {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
