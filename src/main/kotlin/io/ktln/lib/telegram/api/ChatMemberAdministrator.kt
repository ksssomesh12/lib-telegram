package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class ChatMemberAdministrator(
    public final val status: String,
    public final val user: User,
    public final val canBeEdited: Boolean,
    public final val isAnonymous: Boolean,
    public final val canManageChat: Boolean,
    public final val canDeleteMessages: Boolean,
    public final val canManageVoiceChats: Boolean,
    public final val canRestrictMembers: Boolean,
    public final val canPromoteMembers: Boolean,
    public final val canChangeInfo: Boolean,
    public final val canInviteUsers: Boolean,
    public final val canPostMessages: Boolean? = null,
    public final val canEditMessages: Boolean? = null,
    public final val canPinMessages: Boolean? = null,
    public final val customTitle: String? = null
): ChatMember() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): ChatMemberAdministrator {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): ChatMemberAdministrator {
            return dataMap.toDataClass()
        }
    }
}
