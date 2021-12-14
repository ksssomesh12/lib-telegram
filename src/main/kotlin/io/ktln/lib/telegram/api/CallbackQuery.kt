package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.file.InputMedia
import io.ktln.lib.telegram.extension.ReplyMarkup
import io.ktln.lib.telegram.api.game.GameHighScore
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.internal.toDataClass

class CallbackQuery(
    public final val id: String,
    public final val from: User,
    public final val message: Message? = null,
    public final val inlineMessageId: String? = null,
    public final val chatInstance: String,
    public final val data: String? = null,
    public final val gameShortName: String? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): CallbackQuery {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): CallbackQuery {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun answerCallbackQuery(
        text: String? = null,
        showAlert: Boolean? = null,
        url: String? = null,
        cacheTime: Int? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.answerCallbackQuery(
            callbackQueryId = id,
            text = text,
            showAlert = showAlert,
            url = url,
            cacheTime = cacheTime,
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
    ): Any {
        return when {
            inlineMessageId is String -> bot.editMessageText(
                inlineMessageId = inlineMessageId,
                text = text,
                parseMode = parseMode,
                disableWebPagePreview = disableWebPagePreview,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs,
                entities = entities
            )
            message is Message -> message.editMessageText(
                text = text,
                parseMode = parseMode,
                disableWebPagePreview = disableWebPagePreview,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs,
                entities = entities
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun editMessageCaption(
        caption: String? = null,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        parseMode: ParseMode? = null,
        apiKwArgs: Map<String, String>? = null,
        captionEntities: List<MessageEntity>? = null
    ): Message {
        return when {
            inlineMessageId is String -> bot.editMessageCaption(
                caption = caption,
                inlineMessageId = inlineMessageId,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                parseMode = parseMode,
                apiKwArgs = apiKwArgs,
                captionEntities = captionEntities
            )
            message is Message -> message.editMessageCaption(
                caption = caption,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                parseMode = parseMode,
                apiKwArgs = apiKwArgs,
                captionEntities = captionEntities
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun editMessageReplyMarkup(
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        return when {
            inlineMessageId is String -> bot.editMessageReplyMarkup(
                replyMarkup = replyMarkup,
                inlineMessageId = inlineMessageId,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            message is Message -> message.editMessageReplyMarkup(
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun editMessageMedia(
        media: InputMedia,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        return when {
            inlineMessageId is String -> bot.editMessageMedia(
                inlineMessageId = inlineMessageId,
                media = media,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            message is Message -> message.editMessageMedia(
                media = media,
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
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
        return when {
            inlineMessageId is String -> bot.editMessageLiveLocation(
                inlineMessageId = inlineMessageId,
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
            message is Message -> message.editMessageLiveLocation(
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
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun stopMessageLiveLocation(
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return when {
            inlineMessageId is String -> bot.stopMessageLiveLocation(
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            message is Message -> message.stopMessageLiveLocation(
                replyMarkup = replyMarkup,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun setGameScore(
        userId: Long,
        score: Int,
        force: Boolean? = null,
        disableEditMessage: Boolean? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        return when {
            inlineMessageId is String -> bot.setGameScore(
                inlineMessageId = inlineMessageId,
                userId = userId,
                score = score,
                force = force,
                disableEditMessage = disableEditMessage,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            message is Message -> message.setGameScore(
                userId = userId,
                score = score,
                force = force,
                disableEditMessage = disableEditMessage,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun getGameHighScores(
        userId: Long,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): List<GameHighScore> {
        return when {
            inlineMessageId is String -> bot.getGameHighScores(
                inlineMessageId = inlineMessageId,
                userId = userId,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            message is Message -> message.getGameHighScores(
                userId = userId,
                timeOut = timeOut,
                apiKwArgs = apiKwArgs
            )
            else -> throw Exception("Properties message / inlineMessageId are both null !")
        }
    }

    public final suspend fun deleteMessage(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean? {
        return message?.deleteMessage(
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun pinChatMessage(
        disableNotification: Boolean = false,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean? {
        return message?.pinChatMessage(
            disableNotification = disableNotification,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }

    public final suspend fun unpinChatMessage(
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean? {
        return message?.unpinChatMessage(
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
    ): Message? {
        return message?.copyMessage(
            chatId = chatId,
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
