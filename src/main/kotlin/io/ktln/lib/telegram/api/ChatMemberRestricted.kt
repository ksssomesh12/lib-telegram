package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatMemberRestricted(
    public final val status: String,
    public final val user: User,
    public final val isMember: Boolean,
    public final val canChangeInfo: Boolean,
    public final val canInviteUsers: Boolean,
    public final val canPinMessages: Boolean,
    public final val canSendMessages: Boolean,
    public final val canSendMediaMessages: Boolean,
    public final val canSendPolls: Boolean,
    public final val canSendOtherMessages: Boolean,
    public final val canAddWebPagePreviews: Boolean,
    public final val untilDate: Int
): ChatMember() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatMemberRestricted {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatMemberRestricted {
            return dataMap.toDataClass()
        }
    }
}
