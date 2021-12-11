package io.ktln.lib.telegram

public final class VoiceChatParticipantsInvited(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val users: List<User.Data>? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): VoiceChatParticipantsInvited {
            return VoiceChatParticipantsInvited(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): VoiceChatParticipantsInvited {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
