package io.ktln.lib.telegram.api.payment

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.internal.toDataClass

public final data class PreCheckoutQuery(
    public final val id: String,
    public final val from: User,
    public final val currency: String,
    public final val totalAmount: Int,
    public final val invoicePayload: String,
    public final val shippingOptionId: String? = null,
    public final val orderInfo: OrderInfo? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): PreCheckoutQuery {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): PreCheckoutQuery {
            return dataMap.toDataClass()
        }
    }

    public final suspend fun answerPreCheckoutQuery(
        ok: Boolean,
        errorMessage: String? = null,
        timeOut: Float? = null,
        apiKwArgs: Map<String, String?>? = null
    ): Boolean {
        return bot.answerPreCheckoutQuery(
            preCheckoutQueryId = id,
            ok = ok,
            errorMessage = errorMessage,
            timeOut = timeOut,
            apiKwArgs = apiKwArgs
        )
    }
}
