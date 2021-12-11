package io.ktln.lib.telegram

public final class LoginUrl(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val url: String,
        public val forwardText: String? = null,
        public val botUsername: String? = null,
        public val requestWriteAccess: Boolean? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): LoginUrl {
            return LoginUrl(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): LoginUrl {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
