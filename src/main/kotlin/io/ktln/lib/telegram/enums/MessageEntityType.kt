package io.ktln.lib.telegram.enums

public final enum class MessageEntityType(
    public final val value: String
) {
    Bold("bold"),
    BotCommand("bot_command"),
    Cashtag("cashtag"),
    Code("code"),
    Email("email"),
    Hashtag("hashtag"),
    Italic("italic"),
    Mention("mention"),
    PhoneNumber("phone_number"),
    Pre("pre"),
    Strikethrough("strikethrough"),
    TextLink("text_link"),
    TextMention("text_mention"),
    Underline("underline"),
    Url("url")
}
