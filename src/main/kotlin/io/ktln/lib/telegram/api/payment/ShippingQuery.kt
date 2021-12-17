package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.internal.toDataClass

public final data class ShippingQuery(
    public final val id: String,
    public final val from: User,
    public final val invoicePayload: String,
    public final val shippingAddress: ShippingAddress
) {

    public final companion object Static {
        public final lateinit var bot: Bot
        
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ShippingQuery {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun answerShippingQuery(
        ok: Boolean,
        shippingOptions: List<ShippingOption>? = null,
        errorMessage: String? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.answerShippingQuery(
            shippingQueryId = id,
            ok = ok,
            shippingOptions = shippingOptions,
            errorMessage = errorMessage,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
