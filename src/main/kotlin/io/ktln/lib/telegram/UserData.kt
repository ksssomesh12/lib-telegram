package io.ktln.lib.telegram

public final data class UserData
    (
    public val id: Int,
    public val username: String? = null,
    public val firstName: String,
    public val lastName: String? = null,
    public val isBot: Boolean,
    public val languageCode: LanguageCode? = null,
    public val canJoinGroups: Boolean? = null,
    public val canReadAllGroupMessages: Boolean? = null,
    public val supportsInlineQueries: Boolean? = null
    )
{
}
