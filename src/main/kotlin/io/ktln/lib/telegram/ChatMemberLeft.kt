package io.ktln.lib.telegram

public final class ChatMemberLeft(
    private val bot: Bot,
    private val data: Data
    ): ChatMember() {
    public final data class Data(
        public val status: String,
        public val user: User.Data
    ): ChatMember.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatMemberLeft {
            return ChatMemberLeft(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatMemberLeft {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
