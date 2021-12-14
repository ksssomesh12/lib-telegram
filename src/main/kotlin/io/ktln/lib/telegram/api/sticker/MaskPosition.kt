package io.ktln.lib.telegram.api.sticker

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.internal.toDataClass

public final data class MaskPosition(
    public final val point: String,
    public final val xShift: Float,
    public final val yShift: Float,
    public final val scale: Float
) {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): MaskPosition {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): MaskPosition {
            return dataMap.toDataClass()
        }
    }
}
