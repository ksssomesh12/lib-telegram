package io.ktln.lib.telegram

public final class EncryptedCredentials(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val data: String,
        public val hash: String,
        public val secret: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): EncryptedCredentials {
            return EncryptedCredentials(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): EncryptedCredentials {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
