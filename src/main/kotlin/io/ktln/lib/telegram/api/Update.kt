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

    public final fun effectiveChat(
    ): Chat? {
        return when {
            message is Message -> message.chat
            editedMessage is Message -> editedMessage.chat
            channelPost is Message -> channelPost.chat
            editedChannelPost is Message -> editedChannelPost.chat
            callbackQuery?.message is Message -> callbackQuery.message.chat
            myChatMember is ChatMemberUpdated -> myChatMember.chat
            chatMember is ChatMemberUpdated -> chatMember.chat
            chatJoinRequest is ChatJoinRequest -> chatJoinRequest.chat
            else -> null
        }
    }

    public final fun effectiveMessage(
    ): Message? {
        return when {
            message is Message -> message
            editedMessage is Message -> editedMessage
            channelPost is Message -> channelPost
            editedChannelPost is Message -> editedChannelPost
            callbackQuery?.message is Message -> callbackQuery.message
            else -> null
        }
    }

    public final fun effectiveUser(
    ): User? {
        return when {
            message is Message -> message.from
            editedMessage is Message -> editedMessage.from
            inlineQuery is InlineQuery -> inlineQuery.from
            chosenInlineResult is ChosenInlineResult -> chosenInlineResult.from
            callbackQuery is CallbackQuery -> callbackQuery.from
            shippingQuery is ShippingQuery -> shippingQuery.from
            preCheckoutQuery is PreCheckoutQuery -> preCheckoutQuery.from
            pollAnswer is PollAnswer -> pollAnswer.user
            myChatMember is ChatMemberUpdated -> myChatMember.from
            chatMember is ChatMemberUpdated -> chatMember.from
            chatJoinRequest is ChatJoinRequest -> chatJoinRequest.from
            else -> null
        }
    }
}
