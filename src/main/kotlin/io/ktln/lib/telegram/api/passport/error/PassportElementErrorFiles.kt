package io.ktln.lib.telegram.api.passport.error

public final data class PassportElementErrorFiles(
    public final val source: String,
    public final val type: String,
    public final val fileHashes: List<String>,
    public final val message: String
): PassportElementError() {
}
