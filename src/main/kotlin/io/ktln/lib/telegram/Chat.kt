package io.ktln.lib.telegram

public final class Chat(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val id: Long,
        public val type: String,
        public val title: String? = null,
        public val username: String? = null,
        public val firstName: String? = null,
        public val lastName: String? = null,
        public val photo: ChatPhoto.Data? = null,
        public val bio: String? = null,
        public val description: String? = null,
        public val inviteLink: String? = null,
        public val pinnedMessage: Message.Data? = null,
        public val permissions: ChatPermissions.Data? = null,
        public val slowModeDelay: Int? = null,
        public val messageAutoDeleteTime: Int? = null,
        public val stickerSetName: String? = null,
        public val canSetStickerSet: Boolean? = null,
        public val linkedChatId: Long? = null,
        public val location: ChatLocation.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Chat {
            return Chat(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Chat {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
