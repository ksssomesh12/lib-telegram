package io.ktln.lib.telegram

public final class ChatMemberRestricted(
    private val bot: Bot,
    private val data: Data
    ): ChatMember() {
    public final data class Data(
        public val status: String,
        public val user: User.Data,
        public val isMember: Boolean,
        public val canChangeInfo: Boolean,
        public val canInviteUsers: Boolean,
        public val canPinMessages: Boolean,
        public val canSendMessages: Boolean,
        public val canSendMediaMessages: Boolean,
        public val canSendPolls: Boolean,
        public val canSendOtherMessages: Boolean,
        public val canAddWebPagePreviews: Boolean,
        public val untilDate: Int
    ): ChatMember.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatMemberRestricted {
            return ChatMemberRestricted(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatMemberRestricted {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
