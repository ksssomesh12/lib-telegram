package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.enums.LanguageCode
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.api.file.FileInput
import io.ktln.lib.telegram.api.file.InputMedia
import io.ktln.lib.telegram.api.payment.LabeledPrice
import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.extension.ReplyMarkup
import io.ktln.lib.telegram.internal.toDataClass

public final data class User(
    public final val id: Long,
    public final val isBot: Boolean,
    public final val firstName: String,
    public final val lastName: String? = null,
    public final val username: String? = null,
    public final val languageCode: LanguageCode? = null,
    public final val canJoinGroups: Boolean? = null,
    public final val canReadAllGroupMessages: Boolean? = null,
    public final val supportsInlineQueries: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): User {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): User {
            return dataMap.toDataClass()
        }
    }

    private final fun getFullName(): String = if (lastName != null) "$firstName $lastName" else firstName

    public final suspend fun getProfilePhotos(
        offset: Int? = null,
        limit: Int = 100,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): UserProfilePhotos {
        return bot.getUserProfilePhotos(
            userId = id,
            offset = offset,
            limit = limit,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final fun mentionHtml(
        name: String = getFullName()
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun mentionMarkdown(
        name: String = getFullName()
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun mentionMarkdownV2(
        name: String = getFullName()
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final suspend fun pinMessage(
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

    public final suspend fun unpinMessage(
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

    public final suspend fun unpinAllMessages(
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
}
