package io.ktln.lib.telegram.api.inline.content

import io.ktln.lib.telegram.api.payment.LabeledPrice
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputInvoiceMessageContent(
    public final val title: String,
    public final val description: String,
    public final val payload: String,
    public final val providerToken: String,
    public final val currency: String,
    public final val prices: List<LabeledPrice>,
    public final val maxTipAmount: Int? = null,
    public final val suggestedTipAmounts: List<Int>? = null,
    public final val providerData: Any? = null,
    public final val photoUrl: String? = null,
    public final val photoSize: Int? = null,
    public final val photoWidth: Int? = null,
    public final val photoHeight: Int? = null,
    public final val needName: Boolean? = null,
    public final val needPhoneNumber: Boolean? = null,
    public final val needEmail: Boolean? = null,
    public final val needShippingAddress: Boolean? = null,
    public final val sendPhoneNumberToProvider: Boolean? = null,
    public final val sendEmailToProvider: Boolean? = null,
    public final val isFlexible: Boolean? = null
): InputMessageContent() {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputInvoiceMessageContent {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputInvoiceMessageContent {
            return dataMap.toDataClass()
        }
    }
}
