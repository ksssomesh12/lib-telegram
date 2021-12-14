package io.ktln.lib.telegram.enums

public final enum class ChatActionType(
    public final val value: String
) {
    ChooseSticker("choose_sticker"),
    FindLocation("find_location"),
    RecordAudio("record_audio"),
    RecordVideo("record_video"),
    RecordVideoNote("record_video_note"),
    RecordVoice("record_voice"),
    Typing("typing"),
    UploadAudio("upload_audio"),
    UploadDocument("upload_document"),
    UploadPhoto("upload_photo"),
    UploadVideo("upload_video"),
    UploadVideoNote("upload_video_note"),
    UploadVoice("upload_voice"),
}
