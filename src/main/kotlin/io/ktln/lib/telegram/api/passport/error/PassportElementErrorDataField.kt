package io.ktln.lib.telegram.api.passport.error

public final data class PassportElementErrorDataField(
    public final val source: String,
    public final val type: String,
    public final val fieldName: String,
    public final val dataHash: String,
    public final val message: String
): PassportElementError() {
}
