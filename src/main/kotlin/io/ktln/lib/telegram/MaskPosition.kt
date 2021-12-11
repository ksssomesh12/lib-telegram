package io.ktln.lib.telegram

public final class MaskPosition(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val point: String,
        public val xShift: Float,
        public val yShift: Float,
        public val scale: Float
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): MaskPosition {
            return MaskPosition(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): MaskPosition {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
