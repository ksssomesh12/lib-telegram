package io.ktln.lib.telegram.api

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class BotCommand(
    public final val command: String,
    public final val description: String
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): BotCommand {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): BotCommand {
            return dataMap.toDataClass()
        }

        public final fun fromDataMapList(
            dataMapList: List<Map<String, Any>>
        ): List<BotCommand> {
            val botCommandList = mutableListOf<BotCommand>()
            for (dataMap in dataMapList) {
                botCommandList.add(fromDataMap(dataMap))
            }
            return botCommandList.toList()
        }
    }
}
