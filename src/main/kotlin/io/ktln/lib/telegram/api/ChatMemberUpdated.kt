package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatMemberUpdated(
    public final val chat: Chat,
    public final val from: User,
    public final val date: Int,
    public final val oldMember: ChatMember,
    public final val newMember: ChatMember,
    public final val inviteLink: ChatInviteLink? = null
): ChatMember() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatMemberUpdated {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatMemberUpdated {
            return dataMap.toDataClass()
        }
    }
}
