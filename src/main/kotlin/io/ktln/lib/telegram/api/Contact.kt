package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class Contact(
    public final val phoneNumber: String,
    public final val firstName: String,
    public final val lastName: String? = null,
    public final val userId: Long? = null,
    public final val vcard: String? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Contact {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Contact {
            return dataMap.toDataClass()
        }
    }
}
