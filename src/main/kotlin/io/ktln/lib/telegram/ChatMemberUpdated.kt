package io.ktln.lib.telegram

public final class ChatMemberUpdated(
    private val bot: Bot,
    private val data: Data
    ): ChatMember() {
    public final data class Data(
        public val chat: Chat.Data,
        public val from: User.Data,
        public val date: Int,
        public val oldMember: ChatMember.Data,
        public val newMember: ChatMember.Data,
        public val inviteLink: ChatInviteLink.Data? = null
    ): ChatMember.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatMemberUpdated {
            return ChatMemberUpdated(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatMemberUpdated {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
