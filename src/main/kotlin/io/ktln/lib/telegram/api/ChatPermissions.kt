package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatPermissions(
    public final val canSendMessages: Boolean? = null,
    public final val canSendMediaMessages: Boolean? = null,
    public final val canSendPolls: Boolean? = null,
    public final val canSendOtherMessages: Boolean? = null,
    public final val canAddWebPagePreviews: Boolean? = null,
    public final val canChangeInfo: Boolean? = null,
    public final val canInviteUsers: Boolean? = null,
    public final val canPinMessages: Boolean? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatPermissions {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatPermissions {
            return dataMap.toDataClass()
        }
    }
}
