package io.ktln.lib.telegram.api.file

import io.ktln.lib.telegram.extension.Bot
import io.ktln.lib.telegram.api.MessageEntity
import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.internal.toDataClass

public final data class InputMediaAudio(
    public final val type: String,
    public final val media: String,
    public final val thumb: InputFile? = null,
    public final val caption: String? = null,
    public final val parseMode: ParseMode? = null,
    public final val captionEntities: List<MessageEntity>? = null,
    public final val duration: Int? = null,
    public final val performer: String? = null,
    public final val title: String? = null
): InputMedia() {
    @Transient private final lateinit var bot: Bot
    
    public final fun getBot() = bot

    public final fun setBot(
        bot: Bot
    ): InputMediaAudio {
        this.bot = bot
        return this
    }
    
    public final companion object Static {
        public final fun fromDataMap(
            dataMap: Map<String, Any>
        ): InputMediaAudio {
            return dataMap.toDataClass()
        }
    }
}
