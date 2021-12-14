package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.api.file.FileInput
import io.ktln.lib.telegram.api.file.InputMedia
import io.ktln.lib.telegram.api.payment.LabeledPrice
import io.ktln.lib.telegram.enums.EndPoint
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.enums.PayloadDataKey
import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.extension.ReplyMarkup
import io.ktln.lib.telegram.internal.toDataClass

public final data class Chat(
    public final val id: Long,
    public final val type: String,
    public final val title: String? = null,
    public final val username: String? = null,
    public final val firstName: String? = null,
    public final val lastName: String? = null,
    public final val photo: ChatPhoto? = null,
    public final val bio: String? = null,
    public final val hasPrivateForwards: Boolean? = null,
    public final val description: String? = null,
    public final val inviteLink: String? = null,
    public final val pinnedMessage: Message? = null,
    public final val permissions: ChatPermissions? = null,
    public final val allMembersAreAdministrators: Boolean? = null,
    public final val slowModeDelay: Int? = null,
    public final val messageAutoDeleteTime: Int? = null,
    public final val hasProtectedContent: Boolean? = null,
    public final val stickerSetName: String? = null,
    public final val canSetStickerSet: Boolean? = null,
    public final val linkedChatId: Long? = null,
    public final val location: ChatLocation? = null
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Chat {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Chat {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun leaveChat(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.leaveChat(
            chatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun getChatAdministrators(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): List<ChatMember> {
        return bot.getChatAdministrators(
            chatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun getChatMemberCount(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Int {
        return bot.getChatMemberCount(
            chatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun getChatMember(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): ChatMember {
        return bot.getChatMember(
            chatId = id,
            userId = userId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun banChatMember(
        userId: Long,
        timeOut: Float? = null,
        untilDate: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        revokeMessages: Boolean? = null
    ): Boolean {
        return bot.banChatMember(
            chatId = id,
            userId = userId,
            timeOut = timeOut,
            untilDate = untilDate,
            apiKwArgs = apiKwArgs,
            revokeMessages = revokeMessages
        )
    }

    public final suspend fun banChatSenderChat(
        senderChatId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.banChatSenderChat(
            chatId = id,
            senderChatId = senderChatId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun banChat(
        chatId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.banChatSenderChat(
            chatId = chatId,
            senderChatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unbanChatSenderChat(
        senderChatId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unbanChatSenderChat(
            chatId = id,
            senderChatId = senderChatId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unbanChat(
        chatId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unbanChatSenderChat(
            chatId = chatId,
            senderChatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unbanChatMember(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        onlyIfBanned: Boolean? = null
    ): Boolean {
        return bot.unbanChatMember(
            chatId = id,
            userId = userId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            onlyIfBanned = onlyIfBanned
        )
    }

    public final suspend fun promoteChatMember(
        userId: Long,
        canChangeInfo: Boolean? = null,
        canPostMessages: Boolean? = null,
        canEditMessages: Boolean? = null,
        canDeleteMessages: Boolean? = null,
        canInviteUsers: Boolean? = null,
        canRestrictMembers: Boolean? = null,
        canPinMessages: Boolean? = null,
        canPromoteMembers: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        isAnonymous: Boolean? = null,
        canManageChat: Boolean? = null,
        canManageVoiceChats: Boolean? = null
    ): Boolean {
        return bot.promoteChatMember(
            chatId = id,
            userId = userId,
            canChangeInfo = canChangeInfo,
            canPostMessages = canPostMessages,
            canEditMessages = canEditMessages,
            canDeleteMessages = canDeleteMessages,
            canInviteUsers = canInviteUsers,
            canRestrictMembers = canRestrictMembers,
            canPinMessages = canPinMessages,
            canPromoteMembers = canPromoteMembers,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            isAnonymous = isAnonymous,
            canManageChat = canManageChat,
            canManageVoiceChats = canManageVoiceChats
        )
    }

    public final suspend fun restrictChatMember(
        userId: Long,
        permissions: ChatPermissions,
        untilDate: Int? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.restrictChatMember(
            chatId = id,
            userId = userId,
            permissions = permissions,
            untilDate = untilDate,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun setChatPermissions(
        permissions: ChatPermissions,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.setChatPermissions(
            chatId = id,
            permissions = permissions,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun setChatAdministratorCustomTitle(
        userId: Long,
        customTitle: String,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.setChatAdministratorCustomTitle(
            chatId = id,
            userId = userId,
            customTitle = customTitle,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun pinChatMessage(
        messageId: Int,
        disableNotification: Boolean = false,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.pinChatMessage(
            chatId = id,
            messageId = messageId,
            disableNotification = disableNotification,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unpinChatMessage(
        messageId: Int? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unpinChatMessage(
            chatId = id,
            messageId = messageId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unpinAllChatMessages(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unpinAllChatMessages(
            chatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendMessage(
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean = false,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        return bot.sendMessage(
            chatId = id,
            text = text,
            parseMode = parseMode,
            disableWebPagePreview = disableWebPagePreview,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            entities = entities,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendPhoto(
        photo: FileInput,
        caption: String? = null,
        parseMode: ParseMode? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendPhoto(
            chatId = id,
            photo = photo,
            caption = caption,
            parseMode = parseMode,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            captionEntities = captionEntities,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            fileName = fileName
        )
    }

    public final suspend fun sendMediaGroup(
        media: List<InputMedia>,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): List<Message> {
        return bot.sendMediaGroup(
            chatId = id,
            media = media,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendAudio(
        audio: FileInput,
        caption: String? = null,
        parseMode: ParseMode? = null,
        duration: Int? = null,
        performer: String? = null,
        title: String? = null,
        thumb: FileInput? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendAudio(
            chatId = id,
            audio = audio,
            caption = caption,
            parseMode = parseMode,
            duration = duration,
            performer = performer,
            title = title,
            thumb = thumb,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            captionEntities = captionEntities,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            fileName = fileName
        )
    }

    public final suspend fun sendChatAction(
        action: String,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.sendChatAction(
            chatId = id,
            action = action,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendContact(
        phoneNumber: String,
        firstName: String,
        lastName: String? = null,
        contact: Contact? = null,
        vcard: String? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendContact(
            chatId = id,
            phoneNumber = phoneNumber,
            firstName = firstName,
            lastName = lastName,
            contact = contact,
            vcard = vcard,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendDice(
        emoji: String? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendDice(
            chatId = id,
            emoji = emoji,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendDocument(
        document: FileInput,
        thumb: FileInput? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        disableContentTypeDetection: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendDocument(
            chatId = id,
            document = document,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            disableContentTypeDetection = disableContentTypeDetection,
            captionEntities = captionEntities,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            fileName = fileName
        )
    }

    public final suspend fun sendGame(
        gameShortName: String,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendGame(
            chatId = id,
            gameShortName = gameShortName,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendInvoice(
        title: String,
        description: String,
        payload: String,
        providerToken: String,
        startParameter: String? = null,
        currency: String,
        prices: List<LabeledPrice>,
        providerData: Any? = null,
        photoUrl: String? = null,
        photoSize: Int? = null,
        photoWidth: Int? = null,
        photoHeight: Int? = null,
        needName: Boolean? = null,
        needPhoneNumber: Boolean? = null,
        needEmail: Boolean? = null,
        needShippingAddress: Boolean? = null,
        sendPhoneNumberToProvider: Boolean? = null,
        sendEmailToProvider: Boolean? = null,
        isFlexible: Boolean? = null,
        maxTipAmount: Int? = null,
        suggestedTipAmounts: List<Int>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.sendInvoice(
            chatId = id,
            title = title,
            description = description,
            payload = payload,
            providerToken = providerToken,
            startParameter = startParameter,
            currency = currency,
            prices = prices,
            providerData = providerData,
            photoUrl = photoUrl,
            photoSize = photoSize,
            photoWidth = photoWidth,
            photoHeight = photoHeight,
            needName = needName,
            needPhoneNumber = needPhoneNumber,
            needEmail = needEmail,
            needShippingAddress = needShippingAddress,
            sendPhoneNumberToProvider = sendPhoneNumberToProvider,
            sendEmailToProvider = sendEmailToProvider,
            isFlexible = isFlexible,
            maxTipAmount = maxTipAmount,
            suggestedTipAmounts = suggestedTipAmounts,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendLocation(
        latitude: Float? = null,
        longitude: Float? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        location: Location? = null,
        livePeriod: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        horizontalAccuracy: Float? = null,
        heading: Int? = null,
        proximityAlertRadius: Int? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendLocation(
            chatId = id,
            latitude = latitude,
            longitude = longitude,
            heading = heading,
            location = location,
            livePeriod = livePeriod,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            horizontalAccuracy = horizontalAccuracy,
            proximityAlertRadius = proximityAlertRadius,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendAnimation(
        animation: FileInput,
        duration: Int? = null,
        width: Int? = null,
        height: Int? = null,
        thumb: FileInput? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendAnimation(
            chatId = id,
            animation = animation,
            thumb = thumb,
            caption = caption,
            parseMode = parseMode,
            duration = duration,
            width = width,
            height = height,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            captionEntities = captionEntities,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            fileName = fileName
        )
    }

    public final suspend fun sendSticker(
        sticker: FileInput,
        timeOut: Float? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.sendSticker(
            chatId = id,
            sticker = sticker,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendVideo(
        video: FileInput,
        duration: Int? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        width: Int? = null,
        height: Int? = null,
        parseMode: ParseMode? = null,
        supportsStreaming: Boolean? = null,
        thumb: FileInput? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendVideo(
            chatId = id,
            video = video,
            caption = caption,
            parseMode = parseMode,
            supportsStreaming = supportsStreaming,
            duration = duration,
            width = width,
            height = height,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            captionEntities = captionEntities,
            thumb = thumb,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            fileName = fileName
        )
    }

    public final suspend fun sendVenue(
        latitude: Float? = null,
        longitude: Float? = null,
        title: String? = null,
        address: String? = null,
        foursquareId: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        venue: Venue? = null,
        foursquareType: String? = null,
        apiKwArgs: Map<String, String?>? = null,
        googlePlaceId: String? = null,
        googlePlaceType: String? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendVenue(
            chatId = id,
            latitude = latitude,
            longitude = longitude,
            title = title,
            address = address,
            foursquareId = foursquareId,
            foursquareType = foursquareType,
            googlePlaceId = googlePlaceId,
            googlePlaceType = googlePlaceType,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            venue = venue,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendVideoNote(
        videoNote: FileInput,
        duration: Int? = null,
        length: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        thumb: FileInput? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        fileName: String? = null
    ): Message {
        return bot.sendVideoNote(
            chatId = id,
            videoNote = videoNote,
            duration = duration,
            length = length,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            thumb = thumb,
            fileName = fileName,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendVoice(
        voice: FileInput,
        duration: Int? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        parseMode: ParseMode? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendVoice(
            chatId = id,
            voice = voice,
            caption = caption,
            duration = duration,
            parseMode = parseMode,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            captionEntities = captionEntities,
            fileName = fileName,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendPoll(
        question: String,
        options: List<String>,
        isAnonymous: Boolean? = null,
        type: String? = null,
        allowsMultipleAnswers: Boolean? = null,
        correctOptionId: Int? = null,
        isClosed: Boolean? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        explanation: String? = null,
        explanationParseMode: String? = null,
        openPeriod: Int? = null,
        closeDate: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        explanationEntities: List<MessageEntity>? = null
    ): Message {
        return bot.sendPoll(
            chatId = id,
            question = question,
            options = options,
            isAnonymous = isAnonymous,
            type = type,
            allowsMultipleAnswers = allowsMultipleAnswers,
            correctOptionId = correctOptionId,
            isClosed = isClosed,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            explanation = explanation,
            explanationEntities = explanationEntities,
            explanationParseMode = explanationParseMode,
            openPeriod = openPeriod,
            closeDate = closeDate,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun sendCopy(
        fromChatId: Long,
        messageId: Int,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.copyMessage(
            chatId = id,
            fromChatId = fromChatId,
            messageId = messageId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun copyMessage(
        chatId: Long,
        messageId: Int,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.copyMessage(
            chatId = chatId,
            fromChatId = id,
            messageId = messageId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun exportChatInviteLink(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): String {
        return bot.exportChatInviteLink(
            chatId = id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun createChatInviteLink(
        name: String? = null,
        expireDate: Int? = null,
        memberLimit: Int? = null,
        createsJoinRequest: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        return bot.createChatInviteLink(
            chatId = id,
            name = name,
            expireDate = expireDate,
            memberLimit = memberLimit,
            createsJoinRequest = createsJoinRequest,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun editChatInviteLink(
        chatId: Long,
        inviteLink: String,
        name: String? = null,
        expireDate: Int? = null,
        memberLimit: Int? = null,
        createsJoinRequest: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        return bot.editChatInviteLink(
            chatId = chatId,
            inviteLink = inviteLink,
            name = name,
            expireDate = expireDate,
            memberLimit = memberLimit,
            createsJoinRequest = createsJoinRequest,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun revokeChatInviteLink(
        inviteLink: String,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        return bot.revokeChatInviteLink(
            chatId = id,
            inviteLink = inviteLink,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun approveChatJoinRequest(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.approveChatJoinRequest(
            chatId = id,
            userId = userId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun declineChatJoinRequest(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.declineChatJoinRequest(
            chatId = id,
            userId = userId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
