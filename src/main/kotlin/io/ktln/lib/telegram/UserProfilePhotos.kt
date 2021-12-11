package io.ktln.lib.telegram

public final class UserProfilePhotos(
    private val bot: Bot,
    private val data: Data
    ) {
    public final data class Data(
        public val totalCount: Int,
        public val photos: List<List<PhotoSize.Data>>
    )

    public final companion object Static {
        public final fun fromDataMap(
            bot: Bot,
            dataMap: Map<String, Any>
        ): UserProfilePhotos {
            return UserProfilePhotos(
                bot = bot,
                data = dataMap.toDataClass()
            )
        }

        public final fun fromJsonString(
            bot: Bot,
            jsonString: String
        ): UserProfilePhotos {
            return fromDataMap(
                bot = bot,
                dataMap = jsonString.toMap()
            )
        }
    }

    public final fun getData(): Data = data
}
