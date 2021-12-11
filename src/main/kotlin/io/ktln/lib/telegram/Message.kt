package io.ktln.lib.telegram

public final class Message(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val messageId: Int,
        public val from: User.Data? = null,
        public val senderChat: Chat.Data? = null,
        public val date: Int,
        public val chat: Chat.Data,
        public val forwardFrom: User.Data? = null,
        public val forwardFromChat: Chat.Data? = null,
        public val forwardFromMessageId: Int? = null,
        public val forwardSignature: String? = null,
        public val forwardSenderName: String? = null,
        public val forwardDate: Int? = null,
        public val replyToMessage: Data? = null,
        public val viaBot: User.Data? = null,
        public val editDate: Int? = null,
        public val mediaGroupId: String? = null,
        public val authorSignature: String? = null,
        public val text: String? = null,
        public val entities: List<MessageEntity.Data>? = null,
        public val animation: Animation.Data? = null,
        public val audio: Audio.Data? = null,
        public val document: Document.Data? = null,
        public val photo: List<PhotoSize.Data>? = null,
        public val sticker: Sticker.Data? = null,
        public val video: Video.Data? = null,
        public val videoNote: VideoNote.Data? = null,
        public val voice: Voice.Data? = null,
        public val caption: String? = null,
        public val captionEntities: List<MessageEntity.Data>? = null,
        public val contact: Contact.Data? = null,
        public val dice: Dice.Data? = null,
        public val game: Game.Data? = null,
        public val poll: Poll.Data? = null,
        public val venue: Venue.Data? = null,
        public val location: Location.Data? = null,
        public val newChatMembers: List<User.Data>? = null,
        public val leftChatMember: User.Data? = null,
        public val newChatTitle: String? = null,
        public val newChatPhoto: List<PhotoSize.Data>? = null,
        public val deleteChatPhoto: Boolean? = null,
        public val groupChatCreated: Boolean? = null,
        public val supergroupChatCreated: Boolean? = null,
        public val channelChatCreated: Boolean? = null,
        public val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged.Data? = null,
        public val migrateToChatId: Int? = null,
        public val migrateFromChatId: Int? = null,
        public val pinnedMessage: Data? = null,
        public val invoice: Invoice.Data? = null,
        public val successfulPayment: SuccessfulPayment.Data? = null,
        public val connectedWebsite: String? = null,
        public val passportData: Passport.Data? = null,
        public val proximityAlertTriggered: ProximityAlertTriggered.Data? = null,
        public val voiceChatScheduled: VoiceChatScheduled.Data? = null,
        public val voiceChatStarted: VoiceChatStarted.Data? = null,
        public val voiceChatEnded: VoiceChatEnded.Data? = null,
        public val voiceChatParticipantsInvited: VoiceChatParticipantsInvited.Data? = null,
        public val replyMarkup: InlineKeyboardMarkup.Data? = null
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): Message {
            return Message(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): Message {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
