package io.ktln.lib.telegram

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 This class represents a Telegram Bot.

 .. versionadded:: 13.2
 Objects of this class are comparable in terms of equality. Two objects of this class are
 considered equal, if their :attr:`bot` is equal.

 Note:
 Most bot methods have the argument ``api_kwargs`` which allows to pass arbitrary keywords
 to the Telegram API. This can be used to access new features of the API before they were
 incorporated into PTB. However, this is not guaranteed to work, i.e. it will fail for
 passing files.

 Args:
 token (:obj:`str`): Bot's unique authentication.
 base_url (:obj:`str`, optional): Telegram Bot API service URL.
 base_file_url (:obj:`str`, optional): Telegram Bot API file URL.
 request (:obj:`telegram.utils.request.Request`, optional): Pre initialized
 :obj:`telegram.utils.request.Request`.
 private_key (:obj:`bytes`, optional): Private key for decryption of telegram passport data.
 private_key_password (:obj:`bytes`, optional): Password for above private key.
 defaults (:class:`telegram.ext.Defaults`, optional): An object containing default values to
 be used if not set explicitly in the bot methods.

 .. deprecated:: 13.6
 Passing :class:`telegram.ext.Defaults` to :class:`telegram.Bot` is deprecated. If
 you want to use :class:`telegram.ext.Defaults`, please use
 :class:`telegram.ext.ExtBot` instead.
 */
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

    /**
    Use this method to receive incoming updates using long polling.

    Args:
    offset (:obj:`int`, optional): Identifier of the first update to be returned. Must be
    greater by one than the highest among the identifiers of previously received
    updates. By default, updates starting with the earliest unconfirmed update are
    returned. An update is considered confirmed as soon as getUpdates is called with an
    offset higher than its :attr:`telegram.Update.update_id`. The negative offset can
    be specified to retrieve updates starting from -offset update from the end of the
    updates queue. All previous updates will forgotten.
    limit (:obj:`int`, optional): Limits the number of updates to be retrieved. Values
    between 1-100 are accepted. Defaults to ``100``.
    timeout (:obj:`int`, optional): Timeout in seconds for long polling. Defaults to ``0``,
    i.e. usual short polling. Should be positive, short polling should be used for
    testing purposes only.
    read_latency (:obj:`float` | :obj:`int`, optional): Grace time in seconds for receiving
    the reply from server. Will be added to the ``timeout`` value and used as the read
    timeout from server. Defaults to  ``2``.
    allowed_updates (List[:obj:`str`]), optional): A JSON-serialized list the types of
    updates you want your bot to receive. For example, specify ["message",
    "edited_channel_post", "callback_query"] to only receive updates of these types.
    See :class:`telegram.Update` for a complete list of available update types.
    Specify an empty list to receive all updates except
    :attr:`telegram.Update.chat_member` (default). If not specified, the previous
    setting will be used. Please note that this parameter doesn't affect updates
    created before the call to the get_updates, so unwanted updates may be received for
    a short period of time.
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Note:
    1. This method will not work if an outgoing webhook is set up.
    2. In order to avoid getting duplicate updates, recalculate offset after each
    server response.
    3. To take full advantage of this library take a look at :class:`telegram.ext.Updater`

    Returns:
    List[:class:`telegram.Update`]

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to specify a url and receive incoming updates via an outgoing webhook.
    Whenever there is an update for the bot, Telegram will send an HTTPS POST request to the
    specified url, containing a JSON-serialized Update. In case of an unsuccessful request,
    Telegram will give up after a reasonable amount of attempts.

    If you'd like to make sure that the Webhook request comes from Telegram, Telegram
    recommends using a secret path in the URL, e.g. https://www.example.com/<token>. Since
    nobody else knows your bot's token, you can be pretty sure it's us.

    Note:
    The certificate argument should be a file from disk ``open(filename, 'rb')``.

    Args:
    url (:obj:`str`): HTTPS url to send updates to. Use an empty string to remove webhook
    integration.
    certificate (:obj:`filelike`): Upload your public key certificate so that the root
    certificate in use can be checked. See our self-signed guide for details.
    (https://goo.gl/rw7w6Y)
    ip_address (:obj:`str`, optional): The fixed IP address which will be used to send
    webhook requests instead of the IP address resolved through DNS.
    max_connections (:obj:`int`, optional): Maximum allowed number of simultaneous HTTPS
    connections to the webhook for update delivery, 1-100. Defaults to ``40``. Use
    lower values to limit the load on your bot's server, and higher values to increase
    your bot's throughput.
    allowed_updates (List[:obj:`str`], optional): A JSON-serialized list the types of
    updates you want your bot to receive. For example, specify ["message",
    "edited_channel_post", "callback_query"] to only receive updates of these types.
    See :class:`telegram.Update` for a complete list of available update types.
    Specify an empty list to receive all updates except
    :attr:`telegram.Update.chat_member` (default). If not specified, the previous
    setting will be used. Please note that this parameter doesn't affect updates
    created before the call to the set_webhook, so unwanted updates may be received for
    a short period of time.
    drop_pending_updates (:obj:`bool`, optional): Pass :obj:`True` to drop all pending
    updates.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Note:
    1. You will not be able to receive updates using :meth:`get_updates` for long as an
    outgoing webhook is set up.
    2. To use a self-signed certificate, you need to upload your public key certificate
    using certificate parameter. Please upload as InputFile, sending a String will not
    work.
    3. Ports currently supported for Webhooks: ``443``, ``80``, ``88``, ``8443``.

    If you're having any trouble setting up webhooks, please check out this `guide to
    Webhooks`_.

    Returns:
    :obj:`bool` On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to remove webhook integration if you decide to switch back to
    :meth:`get_updates()`.

    Args:
    drop_pending_updates (:obj:`bool`, optional): Pass :obj:`True` to drop all pending
    updates.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to get current webhook status. Requires no parameters.

    If the bot is using :meth:`get_updates`, will return an object with the
    :attr:`telegram.WebhookInfo.url` field empty.

    Args:
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.WebhookInfo`
     */
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

    /**
    A simple method for testing your bot's auth token. Requires no parameters.

    Args:
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.User`: A :class:`telegram.User` instance representing that bot if the
    credentials are valid, :obj:`None` otherwise.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to log out from the cloud Bot API server before launching the bot locally.
    You *must* log out the bot before running it locally, otherwise there is no guarantee that
    the bot will receive updates. After a successful call, you can immediately log in on a
    local server, but will not be able to log in back to the cloud Bot API server for 10
    minutes.

    Args:
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).

    Returns:
    :obj:`True`: On success

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun logOut(
        timeOut: Float = 0.0f
    ): Boolean {
        httpPost(
            endPoint = EndPoint.LogOut,
            timeOut = timeOut
        )
        TODO("Implement Return Type")
    }

    /**
    Use this method to close the bot instance before moving it from one local server to
    another. You need to delete the webhook before calling this method to ensure that the bot
    isn't launched again after server restart. The method will return error 429 in the first
    10 minutes after the bot is launched.

    Args:
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).

    Returns:
    :obj:`True`: On success

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun close(
        timeOut: Float = 0.0f
    ): Boolean {
        httpPost(
            endPoint = EndPoint.Close,
            timeOut = timeOut
        )
        TODO("Implement Return Type")
    }

    /**
    Use this method to send text messages.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    text (:obj:`str`): Text of the message to be sent. Max 4096 characters after entities
    parsing. Also found as :attr:`telegram.constants.MAX_MESSAGE_LENGTH`.
    parse_mode (:obj:`str`): Send Markdown or HTML, if you want Telegram apps to show bold,
    italic, fixed-width text or inline URLs in your bot's message. See the constants in
    :class:`telegram.ParseMode` for the available modes.
    entities (List[:class:`telegram.MessageEntity`], optional): List of special entities
    that appear in message text, which can be specified instead of :attr:`parse_mode`.
    disable_web_page_preview (:obj:`bool`, optional): Disables link previews for links in
    this message.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options.
    A JSON-serialized object for an inline keyboard, custom reply keyboard,
    instructions to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendMessage(
        chatId: Int,
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

    /**
    Use this method to forward messages of any kind. Service messages can't be forwarded.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    from_chat_id (:obj:`int` | :obj:`str`): Unique identifier for the chat where the
    original message was sent (or channel username in the format ``@channelusername``).
    message_id (:obj:`int`): Message identifier in the chat specified in from_chat_id.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun forwardMessage(
        chatId: Int,
        fromChatId: Int,
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

    /**
    Use this method to copy messages of any kind. Service messages and invoice messages can't
    be copied. The method is analogous to the method :meth:`forward_message`, but the copied
    message doesn't have a link to the original message.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    from_chat_id (:obj:`int` | :obj:`str`): Unique identifier for the chat where the
    original message was sent (or channel username in the format ``@channelusername``).
    message_id (:obj:`int`): Message identifier in the chat specified in from_chat_id.
    caption (:obj:`str`, optional): New caption for media, 0-1024 characters after
    entities parsing. If not specified, the original caption is kept.
    parse_mode (:obj:`str`, optional): Mode for parsing entities in the new caption. See
    the constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (:class:`telegram.utils.types.SLT[MessageEntity]`): List of special
    entities that appear in the new caption, which can be specified instead of
    parse_mode
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options.
    A JSON-serialized object for an inline keyboard, custom reply keyboard,
    instructions to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.MessageId`: On success

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun copyMessage(
        chatId: Int,
        fromChatId: Int,
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

    /**
    Use this method to send photos.

    Note:
    The photo argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    photo (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.PhotoSize`): Photo to send.
    Pass a file_id as String to send a photo that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get a photo from the
    Internet, or upload a new photo using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.PhotoSize` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the photo, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    caption (:obj:`str`, optional): Photo caption (may also be used when resending photos
    by file_id), 0-1024 characters after entities parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendPhoto(
        chatId: Int,
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

    /**
    Use this method to send audio files, if you want Telegram clients to display them in the
    music player. Your audio must be in the .mp3 or .m4a format.

    Bots can currently send audio files of up to 50 MB in size, this limit may be changed in
    the future.

    For sending voice messages, use the :meth:`send_voice` method instead.

    Note:
    The audio argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    audio (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Audio`): Audio file to send.
    Pass a file_id as String to send an audio file that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get an audio file from
    the Internet, or upload a new one using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.Audio` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the audio, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    caption (:obj:`str`, optional): Audio caption, 0-1024 characters after entities
    parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    duration (:obj:`int`, optional): Duration of sent audio in seconds.
    performer (:obj:`str`, optional): Performer.
    title (:obj:`str`, optional): Track name.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    thumb (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`, optional): Thumbnail
    of the file sent; can be ignored if
    thumbnail generation for the file is supported server-side. The thumbnail should be
    in JPEG format and less than 200 kB in size. A thumbnail's width and height should
    not exceed 320. Ignored if the file is not uploaded using multipart/form-data.
    Thumbnails can't be reused and can be only uploaded as a new file.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendAudio(
        chatId: Int,
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

    /**
    Use this method to send general files.

    Bots can currently send files of any type of up to 50 MB in size, this limit may be
    changed in the future.

    Note:
    The document argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    document (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Document`): File to send.
    Pass a file_id as String to send a file that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get a file from the
    Internet, or upload a new one using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.Document` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the document, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.
    caption (:obj:`str`, optional): Document caption (may also be used when resending
    documents by file_id), 0-1024 characters after entities parsing.
    disable_content_type_detection (:obj:`bool`, optional): Disables automatic server-side
    content type detection for files uploaded using multipart/form-data.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    thumb (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`, optional): Thumbnail
    of the file sent; can be ignored if
    thumbnail generation for the file is supported server-side. The thumbnail should be
    in JPEG format and less than 200 kB in size. A thumbnail's width and height should
    not exceed 320. Ignored if the file is not uploaded using multipart/form-data.
    Thumbnails can't be reused and can be only uploaded as a new file.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendDocument(
        chatId: Int,
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

    /**
    Use this method to send video files, Telegram clients support mp4 videos
    (other formats may be sent as Document).

    Bots can currently send video files of up to 50 MB in size, this limit may be changed in
    the future.

    Note:
     * The video argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``
     * ``thumb`` will be ignored for small video files, for which Telegram can easily
    generate thumb nails. However, this behaviour is undocumented and might be changed
    by Telegram.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    video (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Video`): Video file to send.
    Pass a file_id as String to send an video file that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get an video file from
    the Internet, or upload a new one using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.Video` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the video, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    duration (:obj:`int`, optional): Duration of sent video in seconds.
    width (:obj:`int`, optional): Video width.
    height (:obj:`int`, optional): Video height.
    caption (:obj:`str`, optional): Video caption (may also be used when resending videos
    by file_id), 0-1024 characters after entities parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    supports_streaming (:obj:`bool`, optional): Pass :obj:`True`, if the uploaded video is
    suitable for streaming.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    thumb (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`, optional): Thumbnail
    of the file sent; can be ignored if
    thumbnail generation for the file is supported server-side. The thumbnail should be
    in JPEG format and less than 200 kB in size. A thumbnail's width and height should
    not exceed 320. Ignored if the file is not uploaded using multipart/form-data.
    Thumbnails can't be reused and can be only uploaded as a new file.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendVideo(
        chatId: Int,
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

    /**
    Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound).
    Bots can currently send animation files of up to 50 MB in size, this limit may be changed
    in the future.

    Note:
    ``thumb`` will be ignored for small files, for which Telegram can easily
    generate thumb nails. However, this behaviour is undocumented and might be changed
    by Telegram.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    animation (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Animation`): Animation to
    send. Pass a file_id as String to send an animation that exists on the Telegram
    servers (recommended), pass an HTTP URL as a String for Telegram to get an
    animation from the Internet, or upload a new animation using multipart/form-data.
    Lastly you can pass an existing :class:`telegram.Animation` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the animation, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    duration (:obj:`int`, optional): Duration of sent animation in seconds.
    width (:obj:`int`, optional): Animation width.
    height (:obj:`int`, optional): Animation height.
    thumb (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`, optional): Thumbnail
    of the file sent; can be ignored if
    thumbnail generation for the file is supported server-side. The thumbnail should be
    in JPEG format and less than 200 kB in size. A thumbnail's width and height should
    not exceed 320. Ignored if the file is not uploaded using multipart/form-data.
    Thumbnails can't be reused and can be only uploaded as a new file.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    caption (:obj:`str`, optional): Animation caption (may also be used when resending
    animations by file_id), 0-1024 characters after entities parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendAnimation(
        chatId: Int,
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

    /**
    Use this method to send audio files, if you want Telegram clients to display the file
    as a playable voice message. For this to work, your audio must be in an .ogg file
    encoded with OPUS (other formats may be sent as Audio or Document). Bots can currently
    send voice messages of up to 50 MB in size, this limit may be changed in the future.

    Note:
    The voice argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    voice (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Voice`): Voice file to send.
    Pass a file_id as String to send an voice file that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get an voice file from
    the Internet, or upload a new one using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.Voice` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the voice, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    caption (:obj:`str`, optional): Voice message caption, 0-1024 characters after entities
    parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    duration (:obj:`int`, optional): Duration of the voice message in seconds.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard,
    instructions to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendVoice(
        chatId: Int,
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

    /**
    As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long.
    Use this method to send video messages.

    Note:
     * The video_note argument can be either a file_id or a file from disk
    ``open(filename, 'rb')``
     * ``thumb`` will be ignored for small video files, for which Telegram can easily
    generate thumb nails. However, this behaviour is undocumented and might be changed
    by Telegram.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    video_note (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.VideoNote`): Video note
    to send. Pass a file_id as String to send a video note that exists on the Telegram
    servers (recommended) or upload a new video using multipart/form-data. Or you can
    pass an existing :class:`telegram.VideoNote` object to send. Sending video notes by
    a URL is currently unsupported.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    filename (:obj:`str`, optional): Custom file name for the video note, when uploading a
    new file. Convenience parameter, useful e.g. when sending files generated by the
    :obj:`tempfile` module.

    .. versionadded:: 13.1
    duration (:obj:`int`, optional): Duration of sent video in seconds.
    length (:obj:`int`, optional): Video width and height, i.e. diameter of the video
    message.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard,
    instructions to remove reply keyboard or to force a reply from the user.
    thumb (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`, optional): Thumbnail
    of the file sent; can be ignored if
    thumbnail generation for the file is supported server-side. The thumbnail should be
    in JPEG format and less than 200 kB in size. A thumbnail's width and height should
    not exceed 320. Ignored if the file is not uploaded using multipart/form-data.
    Thumbnails can't be reused and can be only uploaded as a new file.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendVideoNote(
        chatId: Int,
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

    /**
    Use this method to send a group of photos or videos as an album.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    media (List[:class:`telegram.InputMediaAudio`, :class:`telegram.InputMediaDocument`, \
    :class:`telegram.InputMediaPhoto`, :class:`telegram.InputMediaVideo`]): An array
    describing messages to be sent, must include 2–10 items.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    List[:class:`telegram.Message`]: An array of the sent Messages.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendMediaGroup(
        chatId: Int,
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

    /**
    Use this method to send point on the map.

    Note:
    You can either supply a :obj:`latitude` and :obj:`longitude` or a :obj:`location`.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    latitude (:obj:`float`, optional): Latitude of location.
    longitude (:obj:`float`, optional): Longitude of location.
    location (:class:`telegram.Location`, optional): The location to send.
    horizontal_accuracy (:obj:`int`, optional): The radius of uncertainty for the location,
    measured in meters; 0-1500.
    live_period (:obj:`int`, optional): Period in seconds for which the location will be
    updated, should be between 60 and 86400.
    heading (:obj:`int`, optional): For live locations, a direction in which the user is
    moving, in degrees. Must be between 1 and 360 if specified.
    proximity_alert_radius (:obj:`int`, optional): For live locations, a maximum distance
    for proximity alerts about approaching another chat member, in meters. Must be
    between 1 and 100000 if specified.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard,
    instructions to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendLocation(
        chatId: Int,
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

    /**
    Use this method to edit live location messages sent by the bot or via the bot
    (for inline bots). A location can be edited until its :attr:`telegram.Location.live_period`
    expires or editing is explicitly disabled by a call to :meth:`stop_message_live_location`.

    Note:
    You can either supply a :obj:`latitude` and :obj:`longitude` or a :obj:`location`.

    Args:
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``).
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the message to edit.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    latitude (:obj:`float`, optional): Latitude of location.
    longitude (:obj:`float`, optional): Longitude of location.
    location (:class:`telegram.Location`, optional): The location to send.
    horizontal_accuracy (:obj:`float`, optional): The radius of uncertainty for the
    location, measured in meters; 0-1500.
    heading (:obj:`int`, optional): Direction in which the user is moving, in degrees. Must
    be between 1 and 360 if specified.
    proximity_alert_radius (:obj:`int`, optional): Maximum distance for proximity alerts
    about approaching another chat member, in meters. Must be between 1 and 100000 if
    specified.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for a new inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is not an inline message, the
    edited message is returned, otherwise :obj:`True` is returned.
     */
    public final suspend fun editMessageLiveLocation(
        chatId: Int? = null,
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

    /**
    Use this method to stop updating a live location message sent by the bot or via the bot
    (for inline bots) before live_period expires.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Required if inline_message_id is not specified.
    Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``).
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the sent message with live location to stop.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for a new inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is sent by the bot, the
    sent Message is returned, otherwise :obj:`True` is returned.
     */
    public final suspend fun stopMessageLiveLocation(
        chatId: Int? = null,
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

    /**
    Use this method to send information about a venue.

    Note:
     * You can either supply :obj:`venue`, or :obj:`latitude`, :obj:`longitude`,
    :obj:`title` and :obj:`address` and optionally :obj:`foursquare_id` and
    :obj:`foursquare_type` or optionally :obj:`google_place_id` and
    :obj:`google_place_type`.
     * Foursquare details and Google Pace details are mutually exclusive. However, this
    behaviour is undocumented and might be changed by Telegram.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    latitude (:obj:`float`, optional): Latitude of venue.
    longitude (:obj:`float`, optional): Longitude of venue.
    title (:obj:`str`, optional): Name of the venue.
    address (:obj:`str`, optional): Address of the venue.
    foursquare_id (:obj:`str`, optional): Foursquare identifier of the venue.
    foursquare_type (:obj:`str`, optional): Foursquare type of the venue, if known.
    (For example, "arts_entertainment/default", "arts_entertainment/aquarium" or
    "food/icecream".)
    google_place_id (:obj:`str`, optional): Google Places identifier of the venue.
    google_place_type (:obj:`str`, optional): Google Places type of the venue. (See
    `supported types \
    <https://developers.google.com/places/web-service/supported_types>`_.)
    venue (:class:`telegram.Venue`, optional): The venue to send.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendVenue(
        chatId: Int? = null,
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

    /**
    Use this method to send phone contacts.

    Note:
    You can either supply :obj:`contact` or :obj:`phone_number` and :obj:`first_name`
    with optionally :obj:`last_name` and optionally :obj:`vcard`.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    phone_number (:obj:`str`, optional): Contact's phone number.
    first_name (:obj:`str`, optional): Contact's first name.
    last_name (:obj:`str`, optional): Contact's last name.
    vcard (:obj:`str`, optional): Additional data about the contact in the form of a vCard,
    0-2048 bytes.
    contact (:class:`telegram.Contact`, optional): The contact to send.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendContact(
        chatId: Int? = null,
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

    /**
    Use this method to send a native poll.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    question (:obj:`str`): Poll question, 1-300 characters.
    options (List[:obj:`str`]): List of answer options, 2-10 strings 1-100 characters each.
    is_anonymous (:obj:`bool`, optional): :obj:`True`, if the poll needs to be anonymous,
    defaults to :obj:`True`.
    type (:obj:`str`, optional): Poll type, :attr:`telegram.Poll.QUIZ` or
    :attr:`telegram.Poll.REGULAR`, defaults to :attr:`telegram.Poll.REGULAR`.
    allows_multiple_answers (:obj:`bool`, optional): :obj:`True`, if the poll allows
    multiple answers, ignored for polls in quiz mode, defaults to :obj:`False`.
    correct_option_id (:obj:`int`, optional): 0-based identifier of the correct answer
    option, required for polls in quiz mode.
    explanation (:obj:`str`, optional): Text that is shown when a user chooses an incorrect
    answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most
    2 line feeds after entities parsing.
    explanation_parse_mode (:obj:`str`, optional): Mode for parsing entities in the
    explanation. See the constants in :class:`telegram.ParseMode` for the available
    modes.
    explanation_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    open_period (:obj:`int`, optional): Amount of time in seconds the poll will be active
    after creation, 5-600. Can't be used together with :attr:`close_date`.
    close_date (:obj:`int` | :obj:`datetime.datetime`, optional): Point in time (Unix
    timestamp) when the poll will be automatically closed. Must be at least 5 and no
    more than 600 seconds in the future. Can't be used together with
    :attr:`open_period`.
    For timezone naive :obj:`datetime.datetime` objects, the default timezone of the
    bot will be used.
    is_closed (:obj:`bool`, optional): Pass :obj:`True`, if the poll needs to be
    immediately closed. This can be useful for poll preview.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendPoll(
        chatId: Int,
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

    /**
    Use this method to send an animated emoji that will display a random value.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    emoji (:obj:`str`, optional): Emoji on which the dice throw animation is based.
    Currently, must be one of “🎲”, “🎯”, “🏀”, “⚽”, "🎳", or “🎰”. Dice can have
    values 1-6 for “🎲”, “🎯” and "🎳", values 1-5 for “🏀” and “⚽”, and values 1-64
    for “🎰”. Defaults to “🎲”.

    .. versionchanged:: 13.4
    Added the "🎳" emoji.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendDice(
        chatId: Int,
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

    /**
    Use this method when you need to tell the user that something is happening on the bot's
    side. The status is set for 5 seconds or less (when a message arrives from your bot,
    Telegram clients clear its typing status). Telegram only recommends using this method when
    a response from the bot will take a noticeable amount of time to arrive.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    action(:class:`telegram.ChatAction` | :obj:`str`): Type of action to broadcast. Choose
    one, depending on what the user is about to receive. For convenience look at the
    constants in :class:`telegram.ChatAction`
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`:  On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendChatAction(
        chatId: Int,
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

    /**
    Use this method to get a list of profile pictures for a user.

    Args:
    user_id (:obj:`int`): Unique identifier of the target user.
    offset (:obj:`int`, optional): Sequential number of the first photo to be returned.
    By default, all photos are returned.
    limit (:obj:`int`, optional): Limits the number of photos to be retrieved. Values
    between 1-100 are accepted. Defaults to ``100``.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.UserProfilePhotos`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getUserProfilePhotos(
        userId: Int,
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

    /**
    Use this method to get basic info about a file and prepare it for downloading. For the
    moment, bots can download files of up to 20MB in size. The file can then be downloaded
    with :meth:`telegram.File.download`. It is guaranteed that the link will be
    valid for at least 1 hour. When the link expires, a new one can be requested by
    calling get_file again.

    Note:
    This function may not preserve the original file name and MIME type.
    You should save the file's MIME type and name (if available) when the File object
    is received.

    Args:
    file_id (:obj:`str` | :class:`telegram.Animation` | :class:`telegram.Audio` |         \
    :class:`telegram.ChatPhoto` | :class:`telegram.Document` |                   \
    :class:`telegram.PhotoSize` | :class:`telegram.Sticker` |                    \
    :class:`telegram.Video` | :class:`telegram.VideoNote` |                      \
    :class:`telegram.Voice`):
    Either the file identifier or an object that has a file_id attribute
    to get file information about.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.File`

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to ban a user from a group, supergroup or a channel. In the case of
    supergroups and channels, the user will not be able to return to the group on their own
    using invite links, etc., unless unbanned first. The bot must be an administrator in the
    chat for this to work and must have the appropriate admin rights.

    .. versionadded:: 13.7

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target group or username
    of the target supergroup or channel (in the format ``@channelusername``).
    user_id (:obj:`int`): Unique identifier of the target user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    until_date (:obj:`int` | :obj:`datetime.datetime`, optional): Date when the user will
    be unbanned, unix time. If user is banned for more than 366 days or less than 30
    seconds from the current time they are considered to be banned forever. Applied
    for supergroups and channels only.
    For timezone naive :obj:`datetime.datetime` objects, the default timezone of the
    bot will be used.
    revoke_messages (:obj:`bool`, optional): Pass :obj:`True` to delete all messages from
    the chat for the user that is being removed. If :obj:`False`, the user will be able
    to see messages in the group that were sent before the user was removed.
    Always :obj:`True` for supergroups and channels.

    .. versionadded:: 13.4
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun banChatMember(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to unban a previously kicked user in a supergroup or channel.

    The user will *not* return to the group or channel automatically, but will be able to join
    via link, etc. The bot must be an administrator for this to work. By default, this method
    guarantees that after the call the user is not a member of the chat, but will be able to
    join it. So if the user is a member of the chat they will also be *removed* from the chat.
    If you don't want this, use the parameter :attr:`only_if_banned`.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    user_id (:obj:`int`): Unique identifier of the target user.
    only_if_banned (:obj:`bool`, optional): Do nothing if the user is not banned.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool` On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun unbanChatMember(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to restrict a user in a supergroup. The bot must be an administrator in
    the supergroup for this to work and must have the appropriate admin rights. Pass
    :obj:`True` for all boolean parameters to lift restrictions from a user.

    Note:
    Since Bot API 4.4, :meth:`restrict_chat_member` takes the new user permissions in a
    single argument of type :class:`telegram.ChatPermissions`. The old way of passing
    parameters will not keep working forever.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup (in the format @supergroupusername).
    user_id (:obj:`int`): Unique identifier of the target user.
    until_date (:obj:`int` | :obj:`datetime.datetime`, optional): Date when restrictions
    will be lifted for the user, unix time. If user is restricted for more than 366
    days or less than 30 seconds from the current time, they are considered to be
    restricted forever.
    For timezone naive :obj:`datetime.datetime` objects, the default timezone of the
    bot will be used.
    permissions (:class:`telegram.ChatPermissions`): A JSON-serialized object for new user
    permissions.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun restrictChatMember(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to promote or demote a user in a supergroup or a channel. The bot must be
    an administrator in the chat for this to work and must have the appropriate admin rights.
    Pass :obj:`False` for all boolean parameters to demote a user.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    user_id (:obj:`int`): Unique identifier of the target user.
    is_anonymous (:obj:`bool`, optional): Pass :obj:`True`, if the administrator's presence
    in the chat is hidden.
    can_manage_chat (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    access the chat event log, chat statistics, message statistics in channels, see
    channel members, see anonymous administrators in supergroups and ignore slow mode.
    Implied by any other administrator privilege.

    .. versionadded:: 13.4

    can_manage_voice_chats (:obj:`bool`, optional): Pass :obj:`True`, if the administrator
    can manage voice chats.

    .. versionadded:: 13.4

    can_change_info (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    change chat title, photo and other settings.
    can_post_messages (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    create channel posts, channels only.
    can_edit_messages (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    edit messages of other users and can pin messages, channels only.
    can_delete_messages (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    delete messages of other users.
    can_invite_users (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    invite new users to the chat.
    can_restrict_members (:obj:`bool`, optional): Pass :obj:`True`, if the administrator
    can restrict, ban or unban chat members.
    can_pin_messages (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    pin messages, supergroups only.
    can_promote_members (:obj:`bool`, optional): Pass :obj:`True`, if the administrator can
    add new administrators with a subset of his own privileges or demote administrators
    that he has promoted, directly or indirectly (promoted by administrators that were
    appointed by him).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun promoteChatMember(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to set a custom title for administrators promoted by the bot in a
    supergroup. The bot must be an administrator for this to work.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username of
    the target supergroup (in the format `@supergroupusername`).
    user_id (:obj:`int`): Unique identifier of the target administrator.
    custom_title (:obj:`str`): New custom title for the administrator; 0-16 characters,
    emoji are not allowed.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setChatAdministratorCustomTitle(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to set default chat permissions for all members. The bot must be an
    administrator in the group or a supergroup for this to work and must have the
    :attr:`telegram.ChatMember.can_restrict_members` admin rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username of
    the target supergroup (in the format `@supergroupusername`).
    permissions (:class:`telegram.ChatPermissions`): New default chat permissions.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setChatPermissions(
        chatId: Int,
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

    /**
    Use this method to generate a new primary invite link for a chat; any previously generated
    link is revoked. The bot must be an administrator in the chat for this to work and must
    have the appropriate admin rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Note:
    Each administrator in a chat generates their own invite links. Bots can't use invite
    links generated by other administrators. If you want your bot to work with invite
    links, it will need to generate its own link using :meth:`export_chat_invite_link` or
    by calling the :meth:`get_chat` method. If your bot needs to generate a new primary
    invite link replacing its previous one, use :attr:`export_chat_invite_link` again.

    Returns:
    :obj:`str`: New invite link on success.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun exportChatInviteLink(
        chatId: Int,
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

    /**
    Use this method to create an additional invite link for a chat. The bot must be an
    administrator in the chat for this to work and must have the appropriate admin rights.
    The link can be revoked using the method :meth:`revoke_chat_invite_link`.

    .. versionadded:: 13.4

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    expire_date (:obj:`int` | :obj:`datetime.datetime`, optional): Date when the link will
    expire. Integer input will be interpreted as Unix timestamp.
    For timezone naive :obj:`datetime.datetime` objects, the default timezone of the
    bot will be used.
    member_limit (:obj:`int`, optional): Maximum number of users that can be members of
    the chat simultaneously after joining the chat via this invite link; 1-99999.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.ChatInviteLink`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun createChatInviteLink(
        chatId: Int,
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

    /**
    Use this method to edit a non-primary invite link created by the bot. The bot must be an
    administrator in the chat for this to work and must have the appropriate admin rights.

    .. versionadded:: 13.4

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    invite_link (:obj:`str`): The invite link to edit.
    expire_date (:obj:`int` | :obj:`datetime.datetime`, optional): Date when the link will
    expire.
    For timezone naive :obj:`datetime.datetime` objects, the default timezone of the
    bot will be used.
    member_limit (:obj:`int`, optional): Maximum number of users that can be members of
    the chat simultaneously after joining the chat via this invite link; 1-99999.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.ChatInviteLink`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun editChatInviteLink(
        chatId: Int,
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

    /**
    Use this method to revoke an invite link created by the bot. If the primary link is
    revoked, a new link is automatically generated. The bot must be an administrator in the
    chat for this to work and must have the appropriate admin rights.

    .. versionadded:: 13.4

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    invite_link (:obj:`str`): The invite link to edit.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.ChatInviteLink`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun revokeChatInviteLink(
        chatId: Int,
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

    /**
    Use this method to set a new profile photo for the chat.

    Photos can't be changed for private chats. The bot must be an administrator in the chat
    for this to work and must have the appropriate admin rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    photo (`filelike object` | :obj:`bytes` | :class:`pathlib.Path`): New chat photo.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setChatPhoto(
        chatId: Int,
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

    /**
    Use this method to delete a chat photo. Photos can't be changed for private chats. The bot
    must be an administrator in the chat for this to work and must have the appropriate admin
    rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun deleteChatPhoto(
        chatId: Int,
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

    /**
    Use this method to change the title of a chat. Titles can't be changed for private chats.
    The bot must be an administrator in the chat for this to work and must have the appropriate
    admin rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    title (:obj:`str`): New chat title, 1-255 characters.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setChatTitle(
        chatId: Int,
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

    /**
    Use this method to change the description of a group, a supergroup or a channel. The bot
    must be an administrator in the chat for this to work and must have the appropriate admin
    rights.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    description (:obj:`str`): New chat description, 0-255 characters.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setChatDescription(
        chatId: Int,
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

    /**
    Use this method to add a message to the list of pinned messages in a chat. If the
    chat is not a private chat, the bot must be an administrator in the chat for this to work
    and must have the :attr:`telegram.ChatMember.can_pin_messages` admin right in a supergroup
    or :attr:`telegram.ChatMember.can_edit_messages` admin right in a channel.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    message_id (:obj:`int`): Identifier of a message to pin.
    disable_notification (:obj:`bool`, optional): Pass :obj:`True`, if it is not necessary
    to send a notification to all chat members about the new pinned message.
    Notifications are always disabled in channels and private chats.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun pinChatMessage(
        chatId: Int,
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

    /**
    Use this method to remove a message from the list of pinned messages in a chat. If the
    chat is not a private chat, the bot must be an administrator in the chat for this to work
    and must have the :attr:`telegram.ChatMember.can_pin_messages` admin right in a
    supergroup or :attr:`telegram.ChatMember.can_edit_messages` admin right in a channel.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    message_id (:obj:`int`, optional): Identifier of a message to unpin. If not specified,
    the most recent pinned message (by sending date) will be unpinned.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun unpinChatMessage(
        chatId: Int,
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

    /**
    Use this method to clear the list of pinned messages in a chat. If the
    chat is not a private chat, the bot must be an administrator in the chat for this
    to work and must have the :attr:`telegram.ChatMember.can_pin_messages` admin right in a
    supergroup or :attr:`telegram.ChatMember.can_edit_messages` admin right in a channel.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun unpinAllChatMessages(
        chatId: Int,
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

    /**
    Use this method for your bot to leave a group, supergroup or channel.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun leaveChat(
        chatId: Int,
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

    /**
    Use this method to get up to date information about the chat (current name of the user for
    one-on-one conversations, current username of a user, group or channel, etc.).

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Chat`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getChat(
        chatId: Int,
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

    /**
    Use this method to get a list of administrators in a chat.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    List[:class:`telegram.ChatMember`]: On success, returns a list of ``ChatMember``
    objects that contains information about all chat administrators except
    other bots. If the chat is a group or a supergroup and no administrators were
    appointed, only the creator will be returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getChatAdministrators(
        chatId: Int,
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

    /**
    Use this method to get the number of members in a chat.

    .. versionadded:: 13.7

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`int`: Number of members in the chat.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getChatMemberCount(
        chatId: Int,
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

    /**
    Use this method to get information about a member of a chat.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup or channel (in the format ``@channelusername``).
    user_id (:obj:`int`): Unique identifier of the target user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.ChatMember`

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getChatMember(
        chatId: Int,
        userId: Int,
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

    /**
    Use this method to set a new group sticker set for a supergroup.
    The bot must be an administrator in the chat for this to work and must have the appropriate
    admin rights. Use the field :attr:`telegram.Chat.can_set_sticker_set` optionally returned
    in :meth:`get_chat` requests to check if the bot can use this method.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup (in the format @supergroupusername).
    sticker_set_name (:obj:`str`): Name of the sticker set to be set as the group
    sticker set.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.
     */
    public final suspend fun setChatStickerSet(
        chatId: Int,
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

    /**
    Use this method to delete a group sticker set from a supergroup. The bot must be an
    administrator in the chat for this to work and must have the appropriate admin rights.
    Use the field :attr:`telegram.Chat.can_set_sticker_set` optionally returned in
    :meth:`get_chat` requests to check if the bot can use this method.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target supergroup (in the format @supergroupusername).
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.
     */
    public final suspend fun deleteChatStickerSet(
        chatId: Int,
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

    /**
    Use this method to send answers to callback queries sent from inline keyboards. The answer
    will be displayed to the user as a notification at the top of the chat screen or as an
    alert.
    Alternatively, the user can be redirected to the specified Game URL. For this option to
    work, you must first create a game for your bot via `@BotFather <https://t.me/BotFather>`_
    and accept the terms. Otherwise, you may use links like t.me/your_bot?start=XXXX that open
    your bot with a parameter.

    Args:
    callback_query_id (:obj:`str`): Unique identifier for the query to be answered.
    text (:obj:`str`, optional): Text of the notification. If not specified, nothing will
    be shown to the user, 0-200 characters.
    show_alert (:obj:`bool`, optional): If :obj:`True`, an alert will be shown by the
    client instead of a notification at the top of the chat screen. Defaults to
    :obj:`False`.
    url (:obj:`str`, optional): URL that will be opened by the user's client. If you have
    created a Game and accepted the conditions via
    `@BotFather <https://t.me/BotFather>`_, specify the URL that
    opens your game - note that this will only work if the query comes from a callback
    game button. Otherwise, you may use links like t.me/your_bot?start=XXXX that open
    your bot with a parameter.
    cache_time (:obj:`int`, optional): The maximum amount of time in seconds that the
    result of the callback query may be cached client-side. Defaults to 0.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool` On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to change the list of the bot's commands. See the
    `Telegram docs <https://core.telegram.org/bots#commands>`_ for more details about bot
    commands.

    Args:
    commands (List[:class:`BotCommand` | (:obj:`str`, :obj:`str`)]): A JSON-serialized list
    of bot commands to be set as the list of the bot's commands. At most 100 commands
    can be specified.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.
    scope (:class:`telegram.BotCommandScope`, optional): A JSON-serialized object,
    describing scope of users for which the commands are relevant. Defaults to
    :class:`telegram.BotCommandScopeDefault`.

    .. versionadded:: 13.7

    language_code (:obj:`str`, optional): A two-letter ISO 639-1 language code. If empty,
    commands will be applied to all users from the given scope, for whose language
    there are no dedicated commands.

    .. versionadded:: 13.7

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to delete the list of the bot's commands for the given scope and user
    language. After deletion,
    `higher level commands <https://core.telegram.org/bots/api#determining-list-of-commands>`_
    will be shown to affected users.

    .. versionadded:: 13.7

    Args:
    scope (:class:`telegram.BotCommandScope`, optional): A JSON-serialized object,
    describing scope of users for which the commands are relevant. Defaults to
    :class:`telegram.BotCommandScopeDefault`.
    language_code (:obj:`str`, optional): A two-letter ISO 639-1 language code. If empty,
    commands will be applied to all users from the given scope, for whose language
    there are no dedicated commands.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to get the current list of the bot's commands for the given scope and user
    language.

    Args:
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.
    scope (:class:`telegram.BotCommandScope`, optional): A JSON-serialized object,
    describing scope of users. Defaults to :class:`telegram.BotCommandScopeDefault`.

    .. versionadded:: 13.7

    language_code (:obj:`str`, optional): A two-letter ISO 639-1 language code or an empty
    string.

    .. versionadded:: 13.7

    Returns:
    List[:class:`telegram.BotCommand`]: On success, the commands set for the bot. An empty
    list is returned if commands are not set.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to edit text and game messages.

    Args:
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``)
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the message to edit.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    text (:obj:`str`): New text of the message, 1-4096 characters after entities parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in your bot's message. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    entities (List[:class:`telegram.MessageEntity`], optional): List of special entities
    that appear in message text, which can be specified instead of :attr:`parse_mode`.
    disable_web_page_preview (:obj:`bool`, optional): Disables link previews for links in
    this message.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for an inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is not an inline message, the
    edited message is returned, otherwise :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun editMessageText(
        text: String,
        chatId: Int? = null,
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

    /**
    Use this method to edit captions of messages.

    Args:
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``)
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the message to edit.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    caption (:obj:`str`, optional): New caption of the message, 0-1024 characters after
    entities parsing.
    parse_mode (:obj:`str`, optional): Send Markdown or HTML, if you want Telegram apps to
    show bold, italic, fixed-width text or inline URLs in the media caption. See the
    constants in :class:`telegram.ParseMode` for the available modes.
    caption_entities (List[:class:`telegram.MessageEntity`], optional): List of special
    entities that appear in message text, which can be specified instead of
    :attr:`parse_mode`.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for an inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is not an inline message, the
    edited message is returned, otherwise :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun editMessageCaption(
        chatId: Int,
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

    /**
    Use this method to edit animation, audio, document, photo, or video messages. If a message
    is part of a message album, then it can be edited only to an audio for audio albums, only
    to a document for document albums and to a photo or a video otherwise. When an inline
    message is edited, a new file can't be uploaded. Use a previously uploaded file via its
    ``file_id`` or specify a URL.

    Args:
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``).
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the message to edit.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    media (:class:`telegram.InputMedia`): An object for a new media content
    of the message.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for an inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is sent by the bot, the
    edited Message is returned, otherwise :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun editMessageMedia(
        chatId: Int,
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

    /**
    Use this method to edit only the reply markup of messages sent by the bot or via the bot
    (for inline bots).

    Args:
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat or username of the target channel
    (in the format ``@channelusername``).
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the message to edit.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for an inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, if edited message is not an inline message, the
    edited message is returned, otherwise :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun editMessageReplyMarkup(
        chatId: Int,
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

    /**
    Use this method to stop a poll which was sent by the bot.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    message_id (:obj:`int`): Identifier of the original message with the poll.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for a new message inline keyboard.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Poll`: On success, the stopped Poll with the final results is
    returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun stopPoll(
        chatId: Int,
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

    /**
    Use this method to delete a message, including service messages, with the following
    limitations:

    - A message can only be deleted if it was sent less than 48 hours ago.
    - A dice message in a private chat can only be deleted if it was sent more than 24
    hours ago.
    - Bots can delete outgoing messages in private chats, groups, and supergroups.
    - Bots can delete incoming messages in private chats.
    - Bots granted :attr:`telegram.ChatMember.can_post_messages` permissions can delete
    outgoing messages in channels.
    - If the bot is an administrator of a group, it can delete any message there.
    - If the bot has :attr:`telegram.ChatMember.can_delete_messages` permission in a
    supergroup or a channel, it can delete any message there.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    message_id (:obj:`int`): Identifier of the message to delete.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun deleteMessage(
        chatId: Int,
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

    /**
    Use this method to send static .WEBP or animated .TGS stickers.

    Note:
    The sticker argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path` | \
    :class:`telegram.Sticker`): Sticker to send.
    Pass a file_id as String to send a file that exists on the Telegram servers
    (recommended), pass an HTTP URL as a String for Telegram to get a .webp file from
    the Internet, or upload a new one using multipart/form-data. Lastly you can pass
    an existing :class:`telegram.Sticker` object to send.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.ReplyMarkup`, optional): Additional interface options. A
    JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
    to remove reply keyboard or to force a reply from the user.
    timeout (:obj:`int` | :obj:`float`, optional): Send file timeout (default: 20 seconds).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendSticker(
        chatId: Int,
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

    /**
    Use this method to get a sticker set.

    Args:
    name (:obj:`str`): Name of the sticker set.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.StickerSet`

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to upload a .png file with a sticker for later use in
    :meth:`create_new_sticker_set` and :meth:`add_sticker_to_set` methods (can be used multiple
    times).

    Note:
    The png_sticker argument can be either a file_id, an URL or a file from disk
    ``open(filename, 'rb')``

    Args:
    user_id (:obj:`int`): User identifier of sticker file owner.
    png_sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`):
    Png image with the sticker,
    must be up to 512 kilobytes in size, dimensions must not exceed 512px,
    and either width or height must be exactly 512px.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.File`: On success, the uploaded File is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun uploadStickerFile(
        userId: Int,
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

    /**
    Use this method to create new sticker set owned by a user.
    The bot will be able to edit the created sticker set.
    You must use exactly one of the fields ``png_sticker`` or ``tgs_sticker``.

    Warning:
    As of API 4.7 ``png_sticker`` is an optional argument and therefore the order of the
    arguments had to be changed. Use keyword arguments to make sure that the arguments are
    passed correctly.

    Note:
    The png_sticker and tgs_sticker argument can be either a file_id, an URL or a file from
    disk ``open(filename, 'rb')``

    Args:
    user_id (:obj:`int`): User identifier of created sticker set owner.
    name (:obj:`str`): Short name of sticker set, to be used in t.me/addstickers/ URLs
    (e.g., animals). Can contain only english letters, digits and underscores.
    Must begin with a letter, can't contain consecutive underscores and
    must end in "_by_<bot username>". <bot_username> is case insensitive.
    1-64 characters.
    title (:obj:`str`): Sticker set title, 1-64 characters.
    png_sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`, \
    optional): Png image with the sticker,
    must be up to 512 kilobytes in size, dimensions must not exceed 512px,
    and either width or height must be exactly 512px. Pass a file_id as a String to
    send a file that already exists on the Telegram servers, pass an HTTP URL as a
    String for Telegram to get a file from the Internet, or upload a new one
    using multipart/form-data.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    tgs_sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`, \
    optional): TGS animation with the sticker,
    uploaded using multipart/form-data. See
    https://core.telegram.org/animated_stickers#technical-requirements for technical
    requirements.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    emojis (:obj:`str`): One or more emoji corresponding to the sticker.
    contains_masks (:obj:`bool`, optional): Pass :obj:`True`, if a set of mask stickers
    should be created.
    mask_position (:class:`telegram.MaskPosition`, optional): Position where the mask
    should be placed on faces.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun createNewStickerSet(
        userId: Int,
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

    /**
    Use this method to add a new sticker to a set created by the bot.
    You must use exactly one of the fields ``png_sticker`` or ``tgs_sticker``. Animated
    stickers can be added to animated sticker sets and only to them. Animated sticker sets can
    have up to 50 stickers. Static sticker sets can have up to 120 stickers.

    Warning:
    As of API 4.7 ``png_sticker`` is an optional argument and therefore the order of the
    arguments had to be changed. Use keyword arguments to make sure that the arguments are
    passed correctly.

    Note:
    The png_sticker and tgs_sticker argument can be either a file_id, an URL or a file from
    disk ``open(filename, 'rb')``

    Args:
    user_id (:obj:`int`): User identifier of created sticker set owner.

    name (:obj:`str`): Sticker set name.
    png_sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`, \
    optional): PNG image with the sticker,
    must be up to 512 kilobytes in size, dimensions must not exceed 512px,
    and either width or height must be exactly 512px. Pass a file_id as a String to
    send a file that already exists on the Telegram servers, pass an HTTP URL as a
    String for Telegram to get a file from the Internet, or upload a new one
    using multipart/form-data.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    tgs_sticker (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`, \
    optional): TGS animation with the sticker,
    uploaded using multipart/form-data. See
    https://core.telegram.org/animated_stickers#technical-requirements for technical
    requirements.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    emojis (:obj:`str`): One or more emoji corresponding to the sticker.
    mask_position (:class:`telegram.MaskPosition`, optional): Position where the mask
    should be placed on faces.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun addStickerToSet(
        userId: Int,
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

    /**
    Use this method to move a sticker in a set created by the bot to a specific position.

    Args:
    sticker (:obj:`str`): File identifier of the sticker.
    position (:obj:`int`): New sticker position in the set, zero-based.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to delete a sticker from a set created by the bot.

    Args:
    sticker (:obj:`str`): File identifier of the sticker.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set
    for animated sticker sets only.

    Note:
    The thumb can be either a file_id, an URL or a file from disk ``open(filename, 'rb')``

    Args:
    name (:obj:`str`): Sticker set name
    user_id (:obj:`int`): User identifier of created sticker set owner.
    thumb (:obj:`str` | `filelike object` | :obj:`bytes` | :class:`pathlib.Path`, \
    optional): A PNG image with the thumbnail, must
    be up to 128 kilobytes in size and have width and height exactly 100px, or a TGS
    animation with the thumbnail up to 32 kilobytes in size; see
    https://core.telegram.org/animated_stickers#technical-requirements for animated
    sticker technical requirements. Pass a file_id as a String to send a file that
    already exists on the Telegram servers, pass an HTTP URL as a String for Telegram
    to get a file from the Internet, or upload a new one using multipart/form-data.
    Animated sticker set thumbnail can't be uploaded via HTTP URL.

    .. versionchanged:: 13.2
    Accept :obj:`bytes` as input.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setStickerSetThumb(
        name: String,
        userId: Int,
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

    /**
    Use this method to send answers to an inline query. No more than 50 results per query are
    allowed.

    Warning:
    In most use cases :attr:`current_offset` should not be passed manually. Instead of
    calling this method directly, use the shortcut :meth:`telegram.InlineQuery.answer` with
    ``auto_pagination=True``, which will take care of passing the correct value.

    Args:
    inline_query_id (:obj:`str`): Unique identifier for the answered query.
    results (List[:class:`telegram.InlineQueryResult`] | Callable): A list of results for
    the inline query. In case :attr:`current_offset` is passed, ``results`` may also be
    a callable that accepts the current page index starting from 0. It must return
    either a list of :class:`telegram.InlineQueryResult` instances or :obj:`None` if
    there are no more results.
    cache_time (:obj:`int`, optional): The maximum amount of time in seconds that the
    result of the inline query may be cached on the server. Defaults to ``300``.
    is_personal (:obj:`bool`, optional): Pass :obj:`True`, if results may be cached on
    the server side only for the user that sent the query. By default,
    results may be returned to any user who sends the same query.
    next_offset (:obj:`str`, optional): Pass the offset that a client should send in the
    next query with the same text to receive more results. Pass an empty string if
    there are no more results or if you don't support pagination. Offset length can't
    exceed 64 bytes.
    switch_pm_text (:obj:`str`, optional): If passed, clients will display a button with
    specified text that switches the user to a private chat with the bot and sends the
    bot a start message with the parameter ``switch_pm_parameter``.
    switch_pm_parameter (:obj:`str`, optional): Deep-linking parameter for the /start
    message sent to the bot when user presses the switch button. 1-64 characters,
    only A-Z, a-z, 0-9, _ and - are allowed.
    current_offset (:obj:`str`, optional): The :attr:`telegram.InlineQuery.offset` of
    the inline query to answer. If passed, PTB will automatically take care of
    the pagination for you, i.e. pass the correct ``next_offset`` and truncate the
    results list/get the results from the callable you passed.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Example:
    An inline bot that sends YouTube videos can ask the user to connect the bot to their
    YouTube account to adapt search results accordingly. To do this, it displays a
    'Connect your YouTube account' button above the results, or even before showing any.
    The user presses the button, switches to a private chat with the bot and, in doing so,
    passes a start parameter that instructs the bot to return an oauth link. Once done, the
    bot can offer a switch_inline button so that the user can easily return to the chat
    where they wanted to use the bot's inline capabilities.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Use this method to send invoices.

    Warning:
    As of API 5.2 :attr:`start_parameter` is an optional argument and therefore the order
    of the arguments had to be changed. Use keyword arguments to make sure that the
    arguments are passed correctly.

    .. versionchanged:: 13.5
    As of Bot API 5.2, the parameter :attr:`start_parameter` is optional.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat or username
    of the target channel (in the format ``@channelusername``).
    title (:obj:`str`): Product name, 1-32 characters.
    description (:obj:`str`): Product description, 1-255 characters.
    payload (:obj:`str`): Bot-defined invoice payload, 1-128 bytes. This will not be
    displayed to the user, use for your internal processes.
    provider_token (:obj:`str`): Payments provider token, obtained via
    `@BotFather <https://t.me/BotFather>`_.
    currency (:obj:`str`): Three-letter ISO 4217 currency code.
    prices (List[:class:`telegram.LabeledPrice`)]: Price breakdown, a JSON-serialized list
    of components (e.g. product price, tax, discount, delivery cost, delivery tax,
    bonus, etc.).
    max_tip_amount (:obj:`int`, optional): The maximum accepted amount for tips in the
    smallest units of the currency (integer, not float/double). For example, for a
    maximum tip of US$ 1.45 pass ``max_tip_amount = 145``. See the exp parameter in
    `currencies.json <https://core.telegram.org/bots/payments/currencies.json>`_, it
    shows the number of digits past the decimal point for each currency (2 for the
    majority of currencies). Defaults to ``0``.

    .. versionadded:: 13.5
    suggested_tip_amounts (List[:obj:`int`], optional): A JSON-serialized array of
    suggested amounts of tips in the smallest units of the currency (integer, not
    float/double). At most 4 suggested tip amounts can be specified. The suggested tip
    amounts must be positive, passed in a strictly increased order and must not exceed
    ``max_tip_amount``.

    .. versionadded:: 13.5
    start_parameter (:obj:`str`, optional): Unique deep-linking parameter. If left empty,
     *forwarded copies* of the sent message will have a *Pay* button, allowing
    multiple users to pay directly from the forwarded message, using the same invoice.
    If non-empty, forwarded copies of the sent message will have a *URL* button with a
    deep link to the bot (instead of a *Pay* button), with the value used as the
    start parameter.

    .. versionchanged:: 13.5
    As of Bot API 5.2, this parameter is optional.
    provider_data (:obj:`str` | :obj:`object`, optional): JSON-serialized data about the
    invoice, which will be shared with the payment provider. A detailed description of
    required fields should be provided by the payment provider. When an object is
    passed, it will be encoded as JSON.
    photo_url (:obj:`str`, optional): URL of the product photo for the invoice. Can be a
    photo of the goods or a marketing image for a service. People like it better when
    they see what they are paying for.
    photo_size (:obj:`str`, optional): Photo size.
    photo_width (:obj:`int`, optional): Photo width.
    photo_height (:obj:`int`, optional): Photo height.
    need_name (:obj:`bool`, optional): Pass :obj:`True`, if you require the user's full
    name to complete the order.
    need_phone_number (:obj:`bool`, optional): Pass :obj:`True`, if you require the user's
    phone number to complete the order.
    need_email (:obj:`bool`, optional): Pass :obj:`True`, if you require the user's email
    to complete the order.
    need_shipping_address (:obj:`bool`, optional): Pass :obj:`True`, if you require the
    user's shipping address to complete the order.
    send_phone_number_to_provider (:obj:`bool`, optional): Pass :obj:`True`, if user's
    phone number should be sent to provider.
    send_email_to_provider (:obj:`bool`, optional): Pass :obj:`True`, if user's email
    address should be sent to provider.
    is_flexible (:obj:`bool`, optional): Pass :obj:`True`, if the final price depends on
    the shipping method.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for an inline keyboard. If empty, one 'Pay total price' button will be
    shown. If not empty, the first button must be a Pay button.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendInvoice(
        chatId: Int,
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

    /**
    If you sent an invoice requesting a shipping address and the parameter ``is_flexible`` was
    specified, the Bot API will send an :class:`telegram.Update` with a
    :attr:`Update.shipping_query` field to the bot. Use this method to reply to shipping
    queries.

    Args:
    shipping_query_id (:obj:`str`): Unique identifier for the query to be answered.
    ok (:obj:`bool`): Specify :obj:`True` if delivery to the specified address is possible
    and :obj:`False` if there are any problems (for example, if delivery to the
    specified address is not possible).
    shipping_options (List[:class:`telegram.ShippingOption`]), optional]: Required if ok is
    :obj:`True`. A JSON-serialized array of available shipping options.
    error_message (:obj:`str`, optional): Required if ok is :obj:`False`. Error message in
    human readable form that explains why it is impossible to complete the order (e.g.
    "Sorry, delivery to your desired address is unavailable"). Telegram will display
    this message to the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Once the user has confirmed their payment and shipping details, the Bot API sends the final
    confirmation in the form of an :class:`telegram.Update` with the field
    :attr:`Update.pre_checkout_query`. Use this method to respond to such pre-checkout queries.

    Note:
    The Bot API must receive an answer within 10 seconds after the pre-checkout
    query was sent.

    Args:
    pre_checkout_query_id (:obj:`str`): Unique identifier for the query to be answered.
    ok (:obj:`bool`): Specify :obj:`True` if everything is alright
    (goods are available, etc.) and the bot is ready to proceed with the order. Use
    :obj:`False` if there are any problems.
    error_message (:obj:`str`, optional): Required if ok is :obj:`False`. Error message
    in human readable form that explains the reason for failure to proceed with
    the checkout (e.g. "Sorry, somebody just bought the last of our amazing black
    T-shirts while you were busy filling out your payment details. Please choose a
    different color or garment!"). Telegram will display this message to the user.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
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

    /**
    Informs a user that some of the Telegram Passport elements they provided contains errors.
    The user will not be able to re-submit their Passport to you until the errors are fixed
    (the contents of the field for which you returned the error must change).

    Use this if the data submitted by the user doesn't satisfy the standards your service
    requires for any reason. For example, if a birthday date seems invalid, a submitted
    document is blurry, a scan shows evidence of tampering, etc. Supply some details in the
    error message to make sure the user knows how to correct the issues.

    Args:
    user_id (:obj:`int`): User identifier
    errors (List[:class:`PassportElementError`]): A JSON-serialized array describing the
    errors.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during
    creation of the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :obj:`bool`: On success, :obj:`True` is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun setPassportDataErrors(
        userId: Int,
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

    /**
    Use this method to send a game.

    Args:
    chat_id (:obj:`int` | :obj:`str`): Unique identifier for the target chat.
    game_short_name (:obj:`str`): Short name of the game, serves as the unique identifier
    for the game. Set up your games via `@BotFather <https://t.me/BotFather>`_.
    disable_notification (:obj:`bool`, optional): Sends the message silently. Users will
    receive a notification with no sound.
    reply_to_message_id (:obj:`int`, optional): If the message is a reply, ID of the
    original message.
    allow_sending_without_reply (:obj:`bool`, optional): Pass :obj:`True`, if the message
    should be sent even if the specified replied-to message is not found.
    reply_markup (:class:`telegram.InlineKeyboardMarkup`, optional): A JSON-serialized
    object for a new inline keyboard. If empty, one ‘Play game_title’ button will be
    shown. If not empty, the first button must launch the game.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: On success, the sent Message is returned.

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun sendGame(
        chatId: Int,
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

    /**
    Use this method to set the score of the specified user in a game.

    Args:
    user_id (:obj:`int`): User identifier.
    score (:obj:`int`): New score, must be non-negative.
    force (:obj:`bool`, optional): Pass :obj:`True`, if the high score is allowed to
    decrease. This can be useful when fixing mistakes or banning cheaters.
    disable_edit_message (:obj:`bool`, optional): Pass :obj:`True`, if the game message
    should not be automatically edited to include the current scoreboard.
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat.
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the sent message.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    :class:`telegram.Message`: The edited message, or if the message wasn't sent by the bot
    , :obj:`True`.

    Raises:
    :class:`telegram.error.TelegramError`: If the new score is not greater than the user's
    current score in the chat and force is :obj:`False`.
     */
    public final suspend fun setGameScore(
        userId: Int,
        chatId: Int? = null,
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

    /**
    Use this method to get data for high score tables. Will return the score of the specified
    user and several of their neighbors in a game.

    Note:
    This method will currently return scores for the target user, plus two of their
    closest neighbors on each side. Will also return the top three users if the user and
    his neighbors are not among them. Please note that this behavior is subject to change.

    Args:
    user_id (:obj:`int`): Target user id.
    chat_id (:obj:`int` | :obj:`str`, optional): Required if inline_message_id is not
    specified. Unique identifier for the target chat.
    message_id (:obj:`int`, optional): Required if inline_message_id is not specified.
    Identifier of the sent message.
    inline_message_id (:obj:`str`, optional): Required if chat_id and message_id are not
    specified. Identifier of the inline message.
    timeout (:obj:`int` | :obj:`float`, optional): If this value is specified, use it as
    the read timeout from the server (instead of the one specified during creation of
    the connection pool).
    api_kwargs (:obj:`dict`, optional): Arbitrary keyword arguments to be passed to the
    Telegram API.

    Returns:
    List[:class:`telegram.GameHighScore`]

    Raises:
    :class:`telegram.error.TelegramError`
     */
    public final suspend fun getGameHighScores(
        userId: Int,
        chatId: Int? = null,
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
