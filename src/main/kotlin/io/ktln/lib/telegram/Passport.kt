package io.ktln.lib.telegram

public final class Passport(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val data: List<EncryptedPassportElement.Data>,
        public val credentials: EncryptedCredentials.Data
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Passport {
            return Passport(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Passport {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
