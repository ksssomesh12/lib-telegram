package io.ktln.lib.telegram

public final class ChatInviteLink(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val inviteLink: String,
        public val creator: User.Data,
        public val createsJoinRequest: Boolean,
        public val isPrimary: Boolean,
        public val isRevoked: Boolean,
        public val name: String? = null,
        public val expireDate: Int? = null,
        public val memberLimit: Int? = null,
        public val pendingJoinRequestCount: Int? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatInviteLink {
            return ChatInviteLink(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatInviteLink {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
