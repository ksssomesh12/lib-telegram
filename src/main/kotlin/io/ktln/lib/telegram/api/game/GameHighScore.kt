package io.ktln.lib.telegram.api.game

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.internal.toDataClass

public final data class GameHighScore(
    public final val position: Int,
    public final val user: User,
    public final val score: Int
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): GameHighScore {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): GameHighScore {
            return dataMap.toDataClass()
        }

        public final fun fromDataMapList(
            dataMapList: List<Map<String, Any>>
        ): List<GameHighScore> {
            val gameHighScoreList = mutableListOf<GameHighScore>()
            for (dataMap in dataMapList) {
                gameHighScoreList.add(fromDataMap(dataMap = dataMap))
            }
            return gameHighScoreList.toList()
        }
    }
}
