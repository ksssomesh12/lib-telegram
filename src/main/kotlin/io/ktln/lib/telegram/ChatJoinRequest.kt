package io.ktln.lib.telegram

public final class ChatJoinRequest(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val chat: Chat.Data,
        public val from: User.Data,
        public val date: Int,
        public val bio: String? = null,
        public val inviteLink: ChatInviteLink.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatJoinRequest {
            return ChatJoinRequest(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatJoinRequest {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
