package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.api.file.*
import io.ktln.lib.telegram.api.game.Game
import io.ktln.lib.telegram.api.game.GameHighScore
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.api.passport.PassportData
import io.ktln.lib.telegram.api.payment.Invoice
import io.ktln.lib.telegram.api.payment.LabeledPrice
import io.ktln.lib.telegram.api.payment.SuccessfulPayment
import io.ktln.lib.telegram.api.sticker.Sticker
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.ext.ReplyMarkup
import io.ktln.lib.telegram.internal.toDataClass

public final data class Message(
    public final val messageId: Int,
    public final val from: User? = null,
    public final val senderChat: Chat? = null,
    public final val date: Int,
    public final val chat: Chat,
    public final val forwardFrom: User? = null,
    public final val forwardFromChat: Chat? = null,
    public final val forwardFromMessageId: Int? = null,
    public final val forwardSignature: String? = null,
    public final val forwardSenderName: String? = null,
    public final val forwardDate: Int? = null,
    public final val replyToMessage: Message? = null,
    public final val viaBot: User? = null,
    public final val editDate: Int? = null,
    public final val mediaGroupId: String? = null,
    public final val authorSignature: String? = null,
    public final val text: String? = null,
    public final val entities: List<MessageEntity>? = null,
    public final val animation: Animation? = null,
    public final val audio: Audio? = null,
    public final val document: Document? = null,
    public final val photo: List<PhotoSize>? = null,
    public final val sticker: Sticker? = null,
    public final val video: Video? = null,
    public final val videoNote: VideoNote? = null,
    public final val voice: Voice? = null,
    public final val caption: String? = null,
    public final val captionEntities: List<MessageEntity>? = null,
    public final val contact: Contact? = null,
    public final val dice: Dice? = null,
    public final val game: Game? = null,
    public final val poll: Poll? = null,
    public final val venue: Venue? = null,
    public final val location: Location? = null,
    public final val newChatMembers: List<User>? = null,
    public final val leftChatMember: User? = null,
    public final val newChatTitle: String? = null,
    public final val newChatPhoto: List<PhotoSize>? = null,
    public final val deleteChatPhoto: Boolean? = null,
    public final val groupChatCreated: Boolean? = null,
    public final val supergroupChatCreated: Boolean? = null,
    public final val channelChatCreated: Boolean? = null,
    public final val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged? = null,
    public final val migrateToChatId: Int? = null,
    public final val migrateFromChatId: Int? = null,
    public final val pinnedMessage: Message? = null,
    public final val invoice: Invoice? = null,
    public final val successfulPayment: SuccessfulPayment? = null,
    public final val connectedWebsite: String? = null,
    public final val passportData: PassportData? = null,
    public final val proximityAlertTriggered: ProximityAlertTriggered? = null,
    public final val voiceChatScheduled: VoiceChatScheduled? = null,
    public final val voiceChatStarted: VoiceChatStarted? = null,
    public final val voiceChatEnded: VoiceChatEnded? = null,
    public final val voiceChatParticipantsInvited: VoiceChatParticipantsInvited? = null,
    public final val replyMarkup: InlineKeyboardMarkup? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Message {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Message {
            return dataMap.toDataClass()
        }

        public final fun fromDataMapList(
            dataMapList: List<Map<String, Any>>
        ): List<Message> {
            val messageList = mutableListOf<Message>()
            for (dataMap in dataMapList) {
                messageList.add(fromDataMap(dataMap))
            }
            return messageList.toList()
        }
    }

    public final suspend fun replyText(
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        return bot.sendMessage(
            chatId = chat.id,
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

    public final suspend fun replyMediaGroup(
        media: List<InputMedia>,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): List<Message> {
        return bot.sendMediaGroup(
            chatId = chat.id,
            media = media,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun replyPhoto(
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
            chatId = chat.id,
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

    public final suspend fun replyAudio(
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
            chatId = chat.id,
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

    public final suspend fun replyDocument(
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
            chatId = chat.id,
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

    public final suspend fun replyAnimation(
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
            chatId = chat.id,
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

    public final suspend fun replySticker(
        sticker: FileInput,
        timeOut: Float? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.sendSticker(
            chatId = chat.id,
            sticker = sticker,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun replyVideo(
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
            chatId = chat.id,
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

    public final suspend fun replyVideoNote(
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
            chatId = chat.id,
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

    public final suspend fun replyVoice(
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
            chatId = chat.id,
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

    public final suspend fun replyLocation(
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
            chatId = chat.id,
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

    public final suspend fun replyVenue(
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
            chatId = chat.id,
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

    public final suspend fun replyContact(
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
            chatId = chat.id,
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

    public final suspend fun replyPoll(
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
            chatId = chat.id,
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

    public final suspend fun replyDice(
        emoji: String? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendDice(
            chatId = chat.id,
            emoji = emoji,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun replyChatAction(
        action: String,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.sendChatAction(
            chatId = chat.id,
            action = action,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun replyGame(
        gameShortName: String,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendGame(
            chatId = chat.id,
            gameShortName = gameShortName,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun replyInvoice(
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
            chatId = chat.id,
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

    public final suspend fun forwardMessage(
        chatId: Long,
        disableNotification: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.forwardMessage(
            chatId = chatId,
            fromChatId = chat.id,
            messageId = messageId,
            disableNotification = disableNotification,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun copyMessage(
        chatId: Long,
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
            fromChatId = chat.id,
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

    public final suspend fun replyCopyMessage(
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
            chatId = chat.id,
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

    public final suspend fun editMessageText(
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean? = null,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String>? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        return bot.editMessageText(
            chatId = chat.id,
            messageId = messageId,
            text = text,
            parseMode = parseMode,
            disableWebPagePreview = disableWebPagePreview,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            entities = entities
        )
    }

    public final suspend fun editMessageCaption(
        caption: String? = null,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        parseMode: ParseMode? = null,
        apiKwArgs: Map<String, String>? = null,
        captionEntities: List<MessageEntity>? = null
    ): Message {
        return bot.editMessageCaption(
            chatId = chat.id,
            messageId = messageId,
            caption = caption,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            parseMode = parseMode,
            apiKwArgs = apiKwArgs,
            captionEntities = captionEntities
        )
    }

    public final suspend fun editMessageMedia(
        media: InputMedia,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        return bot.editMessageMedia(
            chatId = chat.id,
            messageId = messageId,
            media = media,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun editMessageReplyMarkup(
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        return bot.editMessageReplyMarkup(
            chatId = chat.id,
            messageId = messageId,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun editMessageLiveLocation(
        latitude: Float? = null,
        longitude: Float? = null,
        location: Location? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null,
        horizontalAccuracy: Float? = null,
        heading: Int? = null,
        proximityAlertRadius: Int? = null
    ): Message {
        return bot.editMessageLiveLocation(
            chatId = chat.id,
            messageId = messageId,
            latitude = latitude,
            longitude = longitude,
            location = location,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs,
            horizontalAccuracy = horizontalAccuracy,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius
        )
    }

    public final suspend fun stopMessageLiveLocation(
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.stopMessageLiveLocation(
            chatId = chat.id,
            messageId = messageId,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun setGameScore(
        userId: Long,
        score: Int,
        force: Boolean? = null,
        disableEditMessage: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.setGameScore(
            chatId = chat.id,
            messageId = messageId,
            userId = userId,
            score = score,
            force = force,
            disableEditMessage = disableEditMessage,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun getGameHighScores(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): List<GameHighScore> {
        return bot.getGameHighScores(
            chatId = chat.id,
            messageId = messageId,
            userId = userId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun deleteMessage(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.deleteMessage(
            chatId = chat.id,
            messageId = messageId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun stopPoll(
        replyMarkup: InlineKeyboardMarkup,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Poll {
        return bot.stopPoll(
            chatId = chat.id,
            messageId = messageId,
            replyMarkup = replyMarkup,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun pinChatMessage(
        disableNotification: Boolean = false,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.pinChatMessage(
            chatId = chat.id,
            messageId = messageId,
            disableNotification = disableNotification,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unpinChatMessage(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unpinChatMessage(
            chatId = chat.id,
            messageId = messageId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final fun parseEntity(
        entity: MessageEntity
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun parseCaptionEntity(
        captionEntity: MessageEntity
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun parseEntities(
        types: List<String>
    ): Map<MessageEntity, String> {
        TODO("Not Implemented Yet !")
    }

    public final fun parseCaptionEntities(
        types: List<String>
    ): Map<MessageEntity, String> {
        TODO("Not Implemented Yet !")
    }
}
