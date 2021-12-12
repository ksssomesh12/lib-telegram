package io.ktln.lib.telegram

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

public final class Bot
    (
    public val baseUrl: String = "https://api.telegram.org/bot",
    public val baseFileUrl: String = "https://api.telegram.org/file/bot",
    public val token: String
    )
{
    private val httpClient = HttpClient(CIO) {
        install(HttpTimeout)
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    // Internal methods

    private final suspend fun httpGet(
        endPoint: EndPoint,
        payloadData: Map<PayloadDataKey, Any?>? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): HttpResponse {
        return httpClient.get("${baseUrl}${token}/${endPoint.value}") {
            body = resolvePayloadData(payloadData) + (apiKwArgs ?: emptyMap())
            contentType(ContentType.Application.Json)
            timeout {
                requestTimeoutMillis = timeOut.toLong()
            }
        } as HttpResponse
    }

    private final suspend fun httpPost(
        endPoint: EndPoint,
        payloadData: Map<PayloadDataKey, Any?>? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): HttpResponse {
        return httpClient.post("${baseUrl}${token}/${endPoint.value}") {
            body = resolvePayloadData(payloadData) + (apiKwArgs ?: emptyMap())
            contentType(ContentType.Application.Json)
            timeout {
                requestTimeoutMillis = timeOut.toLong()
            }
        } as HttpResponse
    }

    private final fun parseFileInput(
        fileInput: FileInput? = null,
        fileName: String? = null
    ): FileInput? {
        return fileInput
    }

    private final fun resolvePayloadData(
        payloadData: Map<PayloadDataKey, Any?>? = null
    ): Map<String, Any?> {
        return payloadData?.mapKeys { it.key.value } ?: emptyMap()
    }

    // [Getting updates](https://core.telegram.org/bots/api#getting-updates)

    public final suspend fun getUpdates(
        offset: Int? = null,
        limit: Int = 100,
        timeOut: Float = 0.0f,
        readLatency: Float = 2.0f,
        allowedUpdates: List<String>? = null,
        apiKwArgs: Map<String, String>? = null
    ): List<Update> {
        httpPost(
            endPoint = EndPoint.GetUpdates,
            payloadData = mapOf(
                PayloadDataKey.Timeout to timeOut,
                PayloadDataKey.Offset to offset,
                PayloadDataKey.Limit to limit,
                PayloadDataKey.AllowedUpdates to allowedUpdates
            ),
            timeOut = readLatency + timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setWebhook(
        url: String,
        certificate: FileInput? = null,
        timeOut: Float = 0.0f,
        maxConnections: Int = 40,
        allowedUpdates: List<String>? = null,
        apiKwArgs: Map<String, String?>? = null,
        ipAddress: String? = null,
        dropPendingUpdates: Boolean? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetWebhook,
            payloadData = mapOf(
                PayloadDataKey.Url to url,
                PayloadDataKey.Certificate to certificate,
                PayloadDataKey.MaxConnections to maxConnections,
                PayloadDataKey.AllowedUpdates to allowedUpdates,
                PayloadDataKey.IpAddress to ipAddress,
                PayloadDataKey.DropPendingUpdates to dropPendingUpdates
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteWebhook(
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        dropPendingUpdates: Boolean? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteWebhook,
            payloadData = mapOf(
                PayloadDataKey.DropPendingUpdates to dropPendingUpdates
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getWebhookInfo(
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): WebhookInfo {
        httpPost(
            endPoint = EndPoint.GetWebhookInfo,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Available methods](https://core.telegram.org/bots/api#available-methods)

    public final suspend fun getMe(
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): User {
        httpPost(
            endPoint = EndPoint.GetMe,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun logOut(
        timeOut: Float = 0.0f
    ): Boolean {
        httpPost(
            endPoint = EndPoint.LogOut,
            timeOut = timeOut
        )
        TODO("Implement Return Type")
    }

    public final suspend fun close(
        timeOut: Float = 0.0f
    ): Boolean {
        httpPost(
            endPoint = EndPoint.Close,
            timeOut = timeOut
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendMessage(
        chatId: Long,
        text: String,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Text to text,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.DisableWebPagePreview to disableWebPagePreview,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.Entities to entities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun forwardMessage(
        chatId: Long,
        fromChatId: Long,
        messageId: Int,
        disableNotification: Boolean? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.ForwardMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.FromChatId to fromChatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.DisableNotification to disableNotification
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun copyMessage(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.CopyMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.FromChatId to fromChatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.CaptionEntities to captionEntities,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendPhoto(
        chatId: Long,
        photo: FileInput,
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
        httpPost(
            endPoint = EndPoint.SendPhoto,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Photo to parseFileInput(
                    fileInput = photo,
                    fileName = fileName
                ),
                PayloadDataKey.Caption to caption,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.CaptionEntities to captionEntities,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendAudio(
        chatId: Long,
        audio: FileInput,
        duration: Int? = null,
        performer: String? = null,
        title: String? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        parseMode: ParseMode? = null,
        thumb: FileInput? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null,
        fileName: String? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendAudio,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Audio to parseFileInput(
                    fileInput = audio,
                    fileName = fileName
                ),
                PayloadDataKey.Duration to duration,
                PayloadDataKey.Performer to performer,
                PayloadDataKey.Title to title,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.Thumb to thumb,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.CaptionEntities to captionEntities,
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendDocument(
        chatId: Long,
        document: FileInput,
        fileName: String? = null,
        caption: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        parseMode: ParseMode? = null,
        thumb: FileInput? = null,
        apiKwArgs: Map<String, String?>? = null,
        disableContentTypeDetection: Boolean? = null,
        allowSendingWithoutReply: Boolean? = null,
        captionEntities: List<MessageEntity>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendDocument,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Document to parseFileInput(
                    fileInput = document,
                    fileName = fileName
                ),
                PayloadDataKey.Caption to caption,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.CaptionEntities to captionEntities,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.DisableContentTypeDetection to disableContentTypeDetection,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Thumb to thumb
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendVideo(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendVideo,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Video to parseFileInput(
                    fileInput = video,
                    fileName = fileName
                ),
                PayloadDataKey.Duration to duration,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.Height to height,
                PayloadDataKey.Width to width,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.SupportsStreaming to supportsStreaming,
                PayloadDataKey.Thumb to thumb,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.CaptionEntities to captionEntities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendAnimation(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendAnimation,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Animation to parseFileInput(
                    fileInput = animation,
                    fileName = fileName
                ),
                PayloadDataKey.Duration to duration,
                PayloadDataKey.Width to width,
                PayloadDataKey.Height to height,
                PayloadDataKey.Thumb to thumb,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.CaptionEntities to captionEntities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendVoice(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendVoice,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Voice to parseFileInput(
                    fileInput = voice,
                    fileName = fileName
                ),
                PayloadDataKey.Duration to duration,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.CaptionEntities to captionEntities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendVideoNote(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendVideoNote,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.VideoNote to parseFileInput(
                    fileInput = videoNote,
                    fileName = fileName
                ),
                PayloadDataKey.Duration to duration,
                PayloadDataKey.Length to length,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Thumb to thumb,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendMediaGroup(
        chatId: Long,
        media: List<InputMedia>,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): List<Message> {
        httpPost(
            endPoint = EndPoint.SendMediaGroup,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Media to media,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendLocation(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendLocation,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Latitude to latitude,
                PayloadDataKey.Longitude to longitude,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Location to location,
                PayloadDataKey.LivePeriod to livePeriod,
                PayloadDataKey.HorizontalAccuracy to horizontalAccuracy,
                PayloadDataKey.Heading to heading,
                PayloadDataKey.ProximityAlertRadius to proximityAlertRadius
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun editMessageLiveLocation(
        chatId: Long? = null,
        messageId: Int? = null,
        inlineMessageId: Int? = null,
        latitude: Float? = null,
        longitude: Float? = null,
        location: Location? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        horizontalAccuracy: Float? = null,
        heading: Int? = null,
        proximityAlertRadius: Int? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.EditMessageLiveLocation,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.Latitude to latitude,
                PayloadDataKey.Longitude to longitude,
                PayloadDataKey.Location to location,
                PayloadDataKey.HorizontalAccuracy to horizontalAccuracy,
                PayloadDataKey.Heading to heading,
                PayloadDataKey.ProximityAlertRadius to proximityAlertRadius,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun stopMessageLiveLocation(
        chatId: Long? = null,
        messageId: Int? = null,
        inlineMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.StopMessageLiveLocation,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendVenue(
        chatId: Long? = null,
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
        httpPost(
            endPoint = EndPoint.SendVenue,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Latitude to latitude,
                PayloadDataKey.Longitude to longitude,
                PayloadDataKey.Title to title,
                PayloadDataKey.Address to address,
                PayloadDataKey.FoursquareId to foursquareId,
                PayloadDataKey.FoursquareType to foursquareType,
                PayloadDataKey.GooglePlaceId to googlePlaceId,
                PayloadDataKey.GooglePlaceType to googlePlaceType,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendContact(
        chatId: Long? = null,
        phoneNumber: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        contact: Contact? = null,
        vcard: String? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendContact,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.PhoneNumber to phoneNumber,
                PayloadDataKey.FirstName to firstName,
                PayloadDataKey.LastName to lastName,
                PayloadDataKey.Vcard to vcard,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendPoll(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendPoll,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Question to question,
                PayloadDataKey.Options to options,
                PayloadDataKey.IsAnonymous to isAnonymous,
                PayloadDataKey.Type to type,
                PayloadDataKey.AllowsMultipleAnswers to allowsMultipleAnswers,
                PayloadDataKey.CorrectOptionId to correctOptionId,
                PayloadDataKey.IsClosed to isClosed,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Explanation to explanation,
                PayloadDataKey.ExplanationParseMode to explanationParseMode,
                PayloadDataKey.OpenPeriod to openPeriod,
                PayloadDataKey.CloseDate to closeDate,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ExplanationEntities to explanationEntities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendDice(
        chatId: Long,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        timeOut: Float = 0.0f,
        emoji: String? = null,
        apiKwArgs: Map<String, String?>? = null,
        allowSendingWithoutReply: Boolean? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendDice,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Emoji to emoji,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun sendChatAction(
        chatId: Long,
        action: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SendChatAction,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Action to action
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getUserProfilePhotos(
        userId: Long,
        offset: Int? = null,
        limit: Int = 100,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): UserProfilePhotos {
        httpPost(
            endPoint = EndPoint.GetUserProfilePhotos,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Offset to offset,
                PayloadDataKey.Limit to limit
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getFile(
        fileId: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): File {
        httpPost(
            endPoint = EndPoint.GetFile,
            payloadData = mapOf(
                PayloadDataKey.FileId to fileId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun banChatMember(
        chatId: Long,
        userId: Long,
        timeOut: Float = 0.0f,
        untilDate: Int? = null,
        apiKwArgs: Map<String, String?>? = null,
        revokeMessages: Boolean? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.BanChatMember,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.UntilDate to untilDate,
                PayloadDataKey.RevokeMessages to revokeMessages
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun unbanChatMember(
        chatId: Long,
        userId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        onlyIfBanned: Boolean? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.UnbanChatMember,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.OnlyIfBanned to onlyIfBanned
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun restrictChatMember(
        chatId: Long,
        userId: Long,
        permissions: ChatPermissions,
        untilDate: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.RestrictChatMember,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.UntilDate to untilDate,
                PayloadDataKey.Permissions to permissions
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun promoteChatMember(
        chatId: Long,
        userId: Long,
        canChangeInfo: Boolean? = null,
        canPostMessages: Boolean? = null,
        canEditMessages: Boolean? = null,
        canDeleteMessages: Boolean? = null,
        canInviteUsers: Boolean? = null,
        canRestrictMembers: Boolean? = null,
        canPinMessages: Boolean? = null,
        canPromoteMembers: Boolean? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        isAnonymous: Boolean? = null,
        canManageChat: Boolean? = null,
        canManageVoiceChats: Boolean? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.PromoteChatMember,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.IsAnonymous to isAnonymous,
                PayloadDataKey.CanChangeInfo to canChangeInfo,
                PayloadDataKey.CanPostMessages to canPostMessages,
                PayloadDataKey.CanEditMessages to canEditMessages,
                PayloadDataKey.CanDeleteMessages to canDeleteMessages,
                PayloadDataKey.CanInviteUsers to canInviteUsers,
                PayloadDataKey.CanRestrictMembers to canRestrictMembers,
                PayloadDataKey.CanPinMessages to canPinMessages,
                PayloadDataKey.CanPromoteMembers to canPromoteMembers,
                PayloadDataKey.CanManageChats to canManageChat,
                PayloadDataKey.CanManageVoiceChats to canManageVoiceChats
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatAdministratorCustomTitle(
        chatId: Long,
        userId: Long,
        customTitle: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatAdministratorCustomTitle,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.CustomTitle to customTitle
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatPermissions(
        chatId: Long,
        permissions: ChatPermissions,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatPermissions,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Permissions to permissions
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun exportChatInviteLink(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): String {
        httpPost(
            endPoint = EndPoint.ExportChatInviteLink,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun createChatInviteLink(
        chatId: Long,
        expireDate: Int? = null,
        memberLimit: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        httpPost(
            endPoint = EndPoint.CreateChatInviteLink,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.ExpireDate to expireDate,
                PayloadDataKey.MemberLimit to memberLimit
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun editChatInviteLink(
        chatId: Long,
        inviteLink: String,
        expireDate: Int? = null,
        memberLimit: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        httpPost(
            endPoint = EndPoint.EditChatInviteLink,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.InviteLink to inviteLink,
                PayloadDataKey.ExpireDate to expireDate,
                PayloadDataKey.MemberLimit to memberLimit
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun revokeChatInviteLink(
        chatId: Long,
        inviteLink: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): ChatInviteLink {
        httpPost(
            endPoint = EndPoint.RevokeChatInviteLink,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.InviteLink to inviteLink
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatPhoto(
        chatId: Long,
        photo: FileInput,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatPhoto,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Photo to photo
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteChatPhoto(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteChatPhoto,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatTitle(
        chatId: Long,
        title: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatTitle,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Title to title
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatDescription(
        chatId: Long,
        description: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatDescription,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Description to description
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun pinChatMessage(
        chatId: Long,
        messageId: Int,
        disableNotification: Boolean = false,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.PinChatMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.DisableNotification to disableNotification
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun unpinChatMessage(
        chatId: Long,
        messageId: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.UnpinChatMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun unpinAllChatMessages(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.UnpinAllChatMessages,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun leaveChat(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.LeaveChat,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getChat(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Chat {
        httpPost(
            endPoint = EndPoint.GetChat,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getChatAdministrators(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): List<ChatMember> {
        httpPost(
            endPoint = EndPoint.GetChatAdministrators,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getChatMemberCount(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Int {
        httpPost(
            endPoint = EndPoint.GetChatMemberCount,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getChatMember(
        chatId: Long,
        userId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): ChatMember {
        httpPost(
            endPoint = EndPoint.GetChatMember,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.UserId to userId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setChatStickerSet(
        chatId: Long,
        stickerSetName: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetChatStickerSet,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.StickerSetName to stickerSetName
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteChatStickerSet(
        chatId: Long,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteChatStickerSet,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun answerCallbackQuery(
        callbackQueryId: String,
        text: String? = null,
        showAlert: Boolean? = null,
        url: String? = null,
        cacheTime: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.AnswerCallbackQuery,
            payloadData = mapOf(
                PayloadDataKey.CallbackQueryId to callbackQueryId,
                PayloadDataKey.Text to text,
                PayloadDataKey.ShowAlert to showAlert,
                PayloadDataKey.Url to url,
                PayloadDataKey.CacheTime to cacheTime
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setMyCommands(
        commands: List<BotCommand>,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null,
        scope: BotCommandScope? = null,
        languageCode: LanguageCode? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetMyCommands,
            payloadData = mapOf(
                PayloadDataKey.Commands to commands,
                PayloadDataKey.Scope to scope,
                PayloadDataKey.LanguageCode to languageCode
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteMyCommands(
        scope: BotCommandScope? = null,
        languageCode: LanguageCode? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteMyCommands,
            payloadData = mapOf(
                PayloadDataKey.Scope to scope,
                PayloadDataKey.LanguageCode to languageCode
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getMyCommands(
        scope: BotCommandScope? = null,
        languageCode: LanguageCode? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): List<BotCommand> {
        httpPost(
            endPoint = EndPoint.GetMyCommands,
            payloadData = mapOf(
                PayloadDataKey.Scope to scope,
                PayloadDataKey.LanguageCode to languageCode
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Updating messages](https://core.telegram.org/bots/api#updating-messages)

    public final suspend fun editMessageText(
        text: String,
        chatId: Long? = null,
        messageId: Int? = null,
        inlineMessageId: Int? = null,
        parseMode: ParseMode? = null,
        disableWebPagePreview: Boolean? = null,
        replyMarkup: InlineKeyboardMarkup? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String>? = null,
        entities: List<MessageEntity>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.EditMessageText,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.Text to text,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.DisableWebPagePreview to disableWebPagePreview,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.Entities to entities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun editMessageCaption(
        chatId: Long,
        messageId: Int,
        inlineMessageId: Int,
        caption: String,
        replyMarkup: InlineKeyboardMarkup,
        timeOut: Float = 0.0f,
        parseMode: ParseMode,
        apiKwArgs: Map<String, String>? = null,
        captionEntities: List<MessageEntity>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.EditMessageCaption,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.Caption to caption,
                PayloadDataKey.ParseMode to parseMode,
                PayloadDataKey.ReplyMarkup to replyMarkup,
                PayloadDataKey.CaptionEntities to captionEntities
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun editMessageMedia(
        chatId: Long,
        messageId: Int,
        inlineMessageId: Int,
        media: InputMedia,
        replyMarkup: InlineKeyboardMarkup,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.EditMessageMedia,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.Media to media,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun editMessageReplyMarkup(
        chatId: Long,
        messageId: Int,
        inlineMessageId: Int,
        replyMarkup: InlineKeyboardMarkup,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.EditMessageReplyMarkup,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun stopPoll(
        chatId: Long,
        messageId: Int,
        replyMarkup: InlineKeyboardMarkup,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Poll {
        httpPost(
            endPoint = EndPoint.StopPoll,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteMessage(
        chatId: Long,
        messageId: Int,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteMessage,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Stickers](https://core.telegram.org/bots/api#stickers)

    public final suspend fun sendSticker(
        chatId: Long,
        sticker: FileInput,
        timeOut: Float = 0.0f,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendSticker,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Sticker to parseFileInput(
                    fileInput = sticker,
                    fileName = ""
                ),
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getStickerSet(
        name: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): StickerSet {
        httpPost(
            endPoint = EndPoint.GetStickerSet,
            payloadData = mapOf(
                PayloadDataKey.Name to name
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun uploadStickerFile(
        userId: Long,
        pngSticker: FileInput,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): File {
        httpPost(
            endPoint = EndPoint.UploadStickerFile,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.PngSticker to parseFileInput(
                    fileInput = pngSticker,
                    fileName = ""
                )
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun createNewStickerSet(
        userId: Long,
        name: String,
        title: String,
        pngSticker: FileInput? = null,
        tgsSticker: FileInput? = null,
        emojis: String,
        containsMasks: Boolean? = null,
        maskPosition: MaskPosition? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.CreateNewStickerSet,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Name to name,
                PayloadDataKey.Title to title,
                PayloadDataKey.Emojis to emojis,
                PayloadDataKey.PngSticker to parseFileInput(
                    fileInput = pngSticker,
                    fileName = ""
                ),
                PayloadDataKey.TgsSticker to parseFileInput(
                    fileInput = tgsSticker,
                    fileName = ""
                ),
                PayloadDataKey.ContainsMasks to containsMasks,
                PayloadDataKey.MaskPosition to maskPosition
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun addStickerToSet(
        userId: Long,
        name: String,
        pngSticker: FileInput? = null,
        tgsSticker: FileInput? = null,
        emojis: String,
        maskPosition: MaskPosition? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.AddStickerToSet,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Name to name,
                PayloadDataKey.Emojis to emojis,
                PayloadDataKey.PngSticker to parseFileInput(
                    fileInput = pngSticker,
                    fileName = ""
                ),
                PayloadDataKey.TgsSticker to parseFileInput(
                    fileInput = tgsSticker,
                    fileName = ""
                ),
                PayloadDataKey.MaskPosition to maskPosition
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setStickerPositionInSet(
        sticker: String,
        position: Int,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetStickerPositionInSet,
            payloadData = mapOf(
                PayloadDataKey.Sticker to sticker,
                PayloadDataKey.Position to position
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun deleteStickerFromSet(
        sticker: String,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.DeleteStickerFromSet,
            payloadData = mapOf(
                PayloadDataKey.Sticker to sticker
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setStickerSetThumb(
        name: String,
        userId: Long,
        thumb: FileInput? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetStickerSetThumb,
            payloadData = mapOf(
                PayloadDataKey.Name to name,
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Thumb to parseFileInput(
                    fileInput = thumb,
                    fileName = ""
                )
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Inline mode](https://core.telegram.org/bots/api#inline-mode)

    public final suspend fun answerInlineQuery(
        inlineQueryId: String,
        results: List<InlineQueryResult>,
        cacheTime: Int = 300,
        isPersonal: Boolean? = null,
        nextOffset: String? = null,
        switchPmText: String? = null,
        switchPmParameter: String? = null,
        currentOffset: String? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.AnswerInlineQuery,
            payloadData = mapOf(
                PayloadDataKey.InlineQueryId to inlineQueryId,
                PayloadDataKey.Results to results,
                PayloadDataKey.CacheTime to cacheTime,
                PayloadDataKey.IsPersonal to isPersonal,
                PayloadDataKey.NextOffset to nextOffset,
                PayloadDataKey.SwitchPmText to switchPmText,
                PayloadDataKey.SwitchPmParameter to switchPmParameter
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Payments](https://core.telegram.org/bots/api#payments)

    public final suspend fun sendInvoice(
        chatId: Long,
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
        httpPost(
            endPoint = EndPoint.SendInvoice,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.Title to title,
                PayloadDataKey.Description to description,
                PayloadDataKey.Payload to payload,
                PayloadDataKey.ProviderToken to providerToken,
                PayloadDataKey.StartParameter to startParameter,
                PayloadDataKey.Currency to currency,
                PayloadDataKey.Prices to prices,
                PayloadDataKey.ProviderData to providerData,
                PayloadDataKey.PhotoUrl to photoUrl,
                PayloadDataKey.PhotoSize to photoSize,
                PayloadDataKey.PhotoWidth to photoWidth,
                PayloadDataKey.PhotoHeight to photoHeight,
                PayloadDataKey.NeedName to needName,
                PayloadDataKey.NeedPhoneNumber to needPhoneNumber,
                PayloadDataKey.NeedEmail to needEmail,
                PayloadDataKey.NeedShippingAddress to needShippingAddress,
                PayloadDataKey.SendPhoneNumberToProvider to sendPhoneNumberToProvider,
                PayloadDataKey.SendEmailToProvider to sendEmailToProvider,
                PayloadDataKey.IsFlexible to isFlexible,
                PayloadDataKey.MaxTipAmount to maxTipAmount,
                PayloadDataKey.SuggestedTipAmounts to suggestedTipAmounts,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun answerShippingQuery(
        shippingQueryId: String,
        ok: Boolean,
        shippingOptions: List<ShippingOption>? = null,
        errorMessage: String? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.AnswerShippingQuery,
            payloadData = mapOf(
                PayloadDataKey.ShippingQueryId to shippingQueryId,
                PayloadDataKey.Ok to ok,
                PayloadDataKey.ShippingOptions to shippingOptions,
                PayloadDataKey.ErrorMessage to errorMessage
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun answerPreCheckoutQuery(
        preCheckoutQueryId: String,
        ok: Boolean,
        errorMessage: String? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.AnswerPreCheckoutQuery,
            payloadData = mapOf(
                PayloadDataKey.PreCheckoutQueryId to preCheckoutQueryId,
                PayloadDataKey.Ok to ok,
                PayloadDataKey.ErrorMessage to errorMessage
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Telegram Passport](https://core.telegram.org/bots/api#telegram-passport)

    public final suspend fun setPassportDataErrors(
        userId: Long,
        errors: List<PassportElementError>,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        httpPost(
            endPoint = EndPoint.SetPassportDataErrors,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Errors to errors
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    // [Games](https://core.telegram.org/bots/api#games)

    public final suspend fun sendGame(
        chatId: Long,
        gameShortName: String,
        disableNotification: Boolean? = null,
        replyToMessageId: Int? = null,
        replyMarkup: ReplyMarkup? = null,
        allowSendingWithoutReply: Boolean? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SendGame,
            payloadData = mapOf(
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.GameShortName to gameShortName,
                PayloadDataKey.DisableNotification to disableNotification,
                PayloadDataKey.ReplyToMessageId to replyToMessageId,
                PayloadDataKey.AllowSendingWithoutReply to allowSendingWithoutReply,
                PayloadDataKey.ReplyMarkup to replyMarkup
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun setGameScore(
        userId: Long,
        chatId: Long? = null,
        messageId: Int? = null,
        inlineMessageId: Int? = null,
        score: Int,
        force: Boolean? = null,
        disableEditMessage: Boolean? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): Message {
        httpPost(
            endPoint = EndPoint.SetGameScore,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.Score to score,
                PayloadDataKey.Force to force,
                PayloadDataKey.DisableEditMessage to disableEditMessage,
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }

    public final suspend fun getGameHighScores(
        userId: Long,
        chatId: Long? = null,
        messageId: Int? = null,
        inlineMessageId: Int? = null,
        timeOut: Float = 0.0f,
        apiKwArgs: Map<String, String?>? = null
    ): List<GameHighScore> {
        httpPost(
            endPoint = EndPoint.GetGameHighScores,
            payloadData = mapOf(
                PayloadDataKey.UserId to userId,
                PayloadDataKey.ChatId to chatId,
                PayloadDataKey.MessageId to messageId,
                PayloadDataKey.InlineMessageId to inlineMessageId,
            ),
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
        TODO("Implement Return Type")
    }
}
