package io.ktln.lib.telegram

public final class EncryptedPassportElement(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val type: String,
        public val data: String?,
        public val phoneNumber: String?,
        public val email: String?,
        public val files: List<PassportFile.Data>?,
        public val frontSide: PassportFile.Data?,
        public val reverseSide: PassportFile.Data?,
        public val selfie: PassportFile.Data?,
        public val translation: List<PassportFile.Data>?,
        public val hash: String
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): EncryptedPassportElement {
            return EncryptedPassportElement(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): EncryptedPassportElement {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
