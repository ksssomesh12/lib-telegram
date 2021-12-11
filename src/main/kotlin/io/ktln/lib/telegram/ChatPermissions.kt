package io.ktln.lib.telegram

public final class ChatPermissions(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val canSendMessages: Boolean? = null,
        public val canSendMediaMessages: Boolean? = null,
        public val canSendPolls: Boolean? = null,
        public val canSendOtherMessages: Boolean? = null,
        public val canAddWebPagePreviews: Boolean? = null,
        public val canChangeInfo: Boolean? = null,
        public val canInviteUsers: Boolean? = null,
        public val canPinMessages: Boolean? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatPermissions {
            return ChatPermissions(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatPermissions {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
