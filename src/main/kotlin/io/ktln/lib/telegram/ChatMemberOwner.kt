package io.ktln.lib.telegram

public final class ChatMemberOwner(
    private val bot: Bot,
    private val data: Data
    ): ChatMember() {
    public final data class Data(
        public val status: String,
        public val user: User.Data,
        public val isAnonymous: Boolean,
        public val customTitle: String? = null
    ): ChatMember.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatMemberOwner {
            return ChatMemberOwner(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatMemberOwner {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
