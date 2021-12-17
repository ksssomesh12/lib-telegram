package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class Poll(
    public final val id: String,
    public final val question: String,
    public final val options: List<PollOption>,
    public final val totalVoterCount: Int,
    public final val isClosed: Boolean,
    public final val isAnonymous: Boolean,
    public final val type: String,
    public final val allowsMultipleAnswers: Boolean,
    public final val correctOptionId: Int? = null,
    public final val explanation: String? = null,
    public final val explanationEntities: List<MessageEntity>? = null,
    public final val openPeriod: Int? = null,
    public final val closeDate: Int? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Poll {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Poll {
            return dataMap.toDataClass()
        }
    }

    public final fun parseExplanationEntity(
        explanationEntity: MessageEntity
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun parseExplanationEntities(
        types: List<String>
    ): Map<MessageEntity, String> {
        TODO("Not Implemented Yet !")
    }
}
