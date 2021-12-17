package io.ktln.lib.telegram.api.passport

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class EncryptedPassportElement(
    public final val type: String,
    public final val data: String? = null,
    public final val phoneNumber: String? = null,
    public final val email: String? = null,
    public final val files: List<PassportFile>? = null,
    public final val frontSide: PassportFile? = null,
    public final val reverseSide: PassportFile? = null,
    public final val selfie: PassportFile? = null,
    public final val translation: List<PassportFile>? = null,
    public final val hash: String
) {

    public final companion object Static {
        public final lateinit var bot: Bot
        
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): EncryptedPassportElement {
            return dataMap.toDataClass()
        }
    }
}
