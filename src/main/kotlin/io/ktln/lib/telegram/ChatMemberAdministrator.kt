package io.ktln.lib.telegram

public final class ChatMemberAdministrator(
    private val bot: Bot,
    private val data: Data
    ): ChatMember() {
    public final data class Data(
        public val status: String,
        public val user: User.Data,
        public val canBeEdited: Boolean,
        public val isAnonymous: Boolean,
        public val canManageChat: Boolean,
        public val canDeleteMessages: Boolean,
        public val canManageVoiceChats: Boolean,
        public val canRestrictMembers: Boolean,
        public val canPromoteMembers: Boolean,
        public val canChangeInfo: Boolean,
        public val canInviteUsers: Boolean,
        public val canPostMessages: Boolean? = null,
        public val canEditMessages: Boolean? = null,
        public val canPinMessages: Boolean? = null,
        public val customTitle: String? = null
    ): ChatMember.Data()

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): ChatMemberAdministrator {
            return ChatMemberAdministrator(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): ChatMemberAdministrator {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
