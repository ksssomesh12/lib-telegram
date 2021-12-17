package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.inline.ChosenInlineResult
import io.ktln.lib.telegram.api.inline.InlineQuery
import io.ktln.lib.telegram.api.payment.PreCheckoutQuery
import io.ktln.lib.telegram.api.payment.ShippingQuery
import io.ktln.lib.telegram.internal.toDataClass

public final data class Update(
    public final val updateId: Int,
    public final val message: Message? = null,
    public final val editedMessage: Message? = null,
    public final val channelPost: Message? = null,
    public final val editedChannelPost: Message? = null,
    public final val inlineQuery: InlineQuery? = null,
    public final val chosenInlineResult: ChosenInlineResult? = null,
    public final val callbackQuery: CallbackQuery? = null,
    public final val shippingQuery: ShippingQuery? = null,
    public final val preCheckoutQuery: PreCheckoutQuery? = null,
    public final val poll: Poll? = null,
    public final val pollAnswer: PollAnswer? = null,
    public final val myChatMember: ChatMemberUpdated? = null,
    public final val chatMember: ChatMemberUpdated? = null,
    public final val chatJoinRequest: ChatJoinRequest? = null
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Update {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Update {
            return dataMap.toDataClass()
        }

        public final fun fromDataMapList(
            dataMapList: List<Map<String, Any>>
        ): List<Update> {
            val updateList = mutableListOf<Update>()
            for (dataMap in dataMapList) {
                updateList.add(fromDataMap(dataMap))
            }
            return updateList.toList()
        }
    }
}
