package io.ktln.lib.telegram.api.game

import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.api.PhotoSize
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.api.file.Animation
import io.ktln.lib.telegram.internal.toDataClass

public final data class Game(
    public final val title: String,
    public final val description: String,
    public final val photo: List<PhotoSize>,
    public final val text: String? = null,
    public final val textEntities: List<MessageEntity>? = null,
    public final val animation: Animation? = null
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Game {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Game {
            return dataMap.toDataClass()
        }
    }

    public final fun parseTextEntity(
        textEntity: MessageEntity
    ): String {
        TODO("Not Implemented Yet !")
    }

    public final fun parseTextEntities(
        types: List<String>
    ): Map<MessageEntity, String> {
        TODO("Not Implemented Yet !")
    }
}
