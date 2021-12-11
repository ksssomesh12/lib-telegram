package io.ktln.lib.telegram

public final class Contact(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val phoneNumber: String,
        public val firstName: String,
        public val lastName: String? = null,
        public val userId: Int? = null,
        public val vcard: String? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Contact {
            return Contact(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Contact {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
