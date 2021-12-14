package io.ktln.lib.telegram.enums

public final enum class ApiConstants(
    public final val value: Any
) {
    BotApiVersion(5.5),
    MaxAnswerCallbackQueryTextLength(200),
    MaxCaptionLength(1024),
    MaxFilesizeDownload(20e6), //20MB
    MaxFilesizeUpload(50e6), //50MB
    MaxInlineQueryResults(50),
    MaxMessageEntities(100),
    MaxMessageLength(4096),
    MaxMessagesPerMinutePerGroup(20),
    MaxMessagesPerSecond(30),
    MaxMessagesPerSecondPerChat(1),
    MaxPhotoSizeUpload(10e6), // 10MB
    MaxPollOptionLength(100),
    MaxPollQuestionLength(300),
    SupportedWebhookPorts(listOf(443, 80, 88, 8443))
}
