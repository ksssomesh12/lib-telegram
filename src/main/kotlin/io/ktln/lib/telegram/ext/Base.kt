package io.ktln.lib.telegram.ext

import io.ktln.lib.telegram.internal.toDataClass

public final data class Base(
    public final val nothing: Nothing
) {
    @Transient private final lateinit var bot: Bot

    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): Base {
        this.bot = bot
        return this
    }

    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): Base {
            return dataMap.toDataClass()
        }

        public final fun fromDataMapList(
            dataMapList: List<Map<String, Any>>
        ): List<Base> {
            val baseList = mutableListOf<Base>()
            for (dataMap in dataMapList) {
                baseList.add(fromDataMap(dataMap))
            }
            return baseList.toList()
        }
    }
}
