package io.ktln.lib.telegram

public final class User(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data
        (
        public val id: Long,
        public val isBot: Boolean,
        public val firstName: String,
        public val lastName: String? = null,
        public val username: String? = null,
        public val languageCode: LanguageCode? = null,
        public val canJoinGroups: Boolean? = null,
        public val canReadAllGroupMessages: Boolean? = null,
        public val supportsInlineQueries: Boolean? = null
        )

    public final companion object Static
    {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): User {
            return User(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): User {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data

    private final fun getFullName(): String = if (data.lastName != null) "${data.firstName} ${data.lastName}" else data.firstName

    /**
    Shortcut for::

    bot.get_user_profile_photos(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see
    :meth:`telegram.Bot.get_user_profile_photos`.
     */
    public final suspend fun getProfilePhotos(
        offset: Int? = null,
        limit: Int = 100,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): UserProfilePhotos {
        return bot.getUserProfilePhotos(
            userId = data.id,
            offset = offset,
            limit = limit,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Args:
    name (:obj:`str`): The name used as a link for the user. Defaults to :attr:`full_name`.

    Returns:
    :obj:`str`: The inline mention for the user as HTML.
     */
    public final fun mentionHtml(
        name: String = getFullName()
    ): String {
        TODO("Not Yet Implemented !")
    }

    /**
    Note:
    :attr:`telegram.ParseMode.MARKDOWN` is a legacy mode, retained by Telegram for
    backward compatibility. You should use :meth:`mention_markdown_v2` instead.

    Args:
    name (:obj:`str`): The name used as a link for the user. Defaults to :attr:`full_name`.

    Returns:
    :obj:`str`: The inline mention for the user as markdown (version 1).
     */
    public final fun mentionMarkdown(
        name: String = getFullName()
    ): String {
        TODO("Not Yet Implemented !")
    }

    /**
    Args:
    name (:obj:`str`): The name used as a link for the user. Defaults to :attr:`full_name`.

    Returns:
    :obj:`str`: The inline mention for the user as markdown (version 2).
     */
    public final fun mentionMarkdownV2(
        name: String = getFullName()
    ): String {
        TODO("Not Yet Implemented !")
    }

    /**
    Shortcut for::

    bot.pin_chat_message(chat_id=update.effective_user.id,
     *args,
     **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.pin_chat_message`.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.
     */
    public final suspend fun pinMessage(
        messageId: Int,
        disableNotification: Boolean = false,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.pinChatMessage(
            chatId = data.id,
            messageId = messageId,
            disableNotification = disableNotification,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.unpin_chat_message(chat_id=update.effective_user.id,
     *args,
     **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.unpin_chat_message`.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.
     */
    public final suspend fun unpinMessage(
        messageId: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unpinChatMessage(
            chatId = data.id,
            messageId = messageId,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.unpin_all_chat_messages(chat_id=update.effective_user.id,
     *args,
     **kwargs)

    For the documentation of the arguments, please see
    :meth:`telegram.Bot.unpin_all_chat_messages`.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.
     */
    public final suspend fun unpinAllMessages(
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.unpinAllChatMessages(
            chatId = data.id,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_message(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_message`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendMessage(
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean = false,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        return bot.sendMessage(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_photo(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_photo`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendPhoto(
        photo: FileInput,
        caption: String? = null,
        parseMode: ParseMode? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendPhoto(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_media_group(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_media_group`.

    Returns:
    List[:class:`telegram.Message`:] On success, instance representing the message posted.
     */
    public final suspend fun sendMediaGroup(
        media: List<InputMedia>,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): List<Message> {
        return bot.sendMediaGroup(
            chatId = data.id,
            media = media,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_audio(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_audio`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendAudio(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_chat_action(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_chat_action`.

    Returns:
    :obj:`True`: On success.
     */
    public final suspend fun sendAction(
        action: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.sendChatAction(
            chatId = data.id,
            action = action,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_contact(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_contact`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendContact(
        phoneNumber: String,
        firstName: String,
        lastName: String? = null,
        contact: Contact? = null,
        vcard: String? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendContact(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_dice(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_dice`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendDice(
        emoji: String? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendDice(
            chatId = data.id,
            emoji = emoji,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_document(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_document`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendDocument(
        document: FileInput,
        thumb: FileInput? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        disableContentTypeDetection: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendDocument(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_game(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_game`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendGame(
        gameShortName: String,
        disableNotification: Boolean = false,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendGame(
            chatId = data.id,
            gameShortName = gameShortName,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_invoice(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_invoice`.

    Warning:
    As of API 5.2 :attr:`start_parameter` is an optional argument and therefore the order
    of the arguments had to be changed. Use keyword arguments to make sure that the
    arguments are passed correctly.

    .. versionchanged:: 13.5
    As of Bot API 5.2, the parameter :attr:`start_parameter` is optional.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.sendInvoice(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_location(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_location`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendLocation(
        latitude: Float? = null,
        longitude: Float? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        location: Location? = null,
        livePeriod: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        horizontalAccuracy: Float? = null,
        heading: Int? = null,
        proximityAlertRadius: Int? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendLocation(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_animation(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_animation`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendAnimation(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_sticker(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_sticker`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendSticker(
        sticker: FileInput,
        timeOut: Float = 0.0f,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.sendSticker(
            chatId = data.id,
            sticker = sticker,
            disableNotification = disableNotification,
            replyToMessageId = replyToMessageId,
            replyMarkup = replyMarkup,
            allowSendingWithoutReply = allowSendingWithoutReply,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    /**
    Shortcut for::

    bot.send_video(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_video`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendVideo(
        video: FileInput,
        duration: Int? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
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
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_venue(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_venue`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendVenue(
        latitude: Float? = null,
        longitude: Float? = null,
        title: String? = null,
        address: String? = null,
        foursquareId: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        venue: Venue? = null,
        foursquareType: String? = null,
        apiKwArgs: Map<String, String?>? = null,
        googlePlaceId: String? = null,
        googlePlaceType: String? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        return bot.sendVenue(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_video_note(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_video_note`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendVideoNote(
        videoNote: FileInput,
        duration: Int? = null,
        length: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        thumb: FileInput? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        fileName: String? = null
    ): Message {
        return bot.sendVideoNote(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_voice(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_voice`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
    public final suspend fun sendVoice(
        voice: FileInput,
        duration: Int? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        parseMode: ParseMode? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        return bot.sendVoice(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.send_poll(update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.send_poll`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        explanation: String? = null,
        explanationParseMode: String? = null,
        openPeriod: Int? = null,
        closeDate: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        explanationEntities: List<MessageEntity>? = null
    ): Message {
        return bot.sendPoll(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.copy_message(chat_id=update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.copy_message`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.copyMessage(
            chatId = data.id,
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

    /**
    Shortcut for::

    bot.copy_message(from_chat_id=update.effective_user.id, *args, **kwargs)

    For the documentation of the arguments, please see :meth:`telegram.Bot.copy_message`.

    Returns:
    :class:`telegram.Message`: On success, instance representing the message posted.
     */
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
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return bot.copyMessage(
            chatId = chatId,
            fromChatId = data.id,
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
