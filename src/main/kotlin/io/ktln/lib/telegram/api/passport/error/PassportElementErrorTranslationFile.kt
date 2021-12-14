package io.ktln.lib.telegram.api.passport.error

public final data class PassportElementErrorTranslationFile(
    public final val source: String,
    public final val type: String,
    public final val fileHash: String,
    public final val message: String
): PassportElementError() {
}
