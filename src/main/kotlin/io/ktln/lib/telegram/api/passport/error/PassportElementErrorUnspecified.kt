package io.ktln.lib.telegram.api.passport.error

public final data class PassportElementErrorUnspecified(
    public final val source: String,
    public final val type: String,
    public final val elementHash: String,
    public final val message: String
): PassportElementError() {
}
