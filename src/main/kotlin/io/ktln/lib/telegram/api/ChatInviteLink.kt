package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatInviteLink(
    public final val inviteLink: String,
    public final val creator: User,
    public final val createsJoinRequest: Boolean,
    public final val isPrimary: Boolean,
    public final val isRevoked: Boolean,
    public final val name: String? = null,
    public final val expireDate: Int? = null,
    public final val memberLimit: Int? = null,
    public final val pendingJoinRequestCount: Int? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatInviteLink {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatInviteLink {
            return dataMap.toDataClass()
        }
    }
}
