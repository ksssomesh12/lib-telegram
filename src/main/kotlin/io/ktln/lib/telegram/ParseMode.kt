package io.ktln.lib.telegram

public final enum class ParseMode(
    public val value: String
    ) {
    HTML("HTML"),
    Markdown("Markdown"),
    MarkdownV2("MarkdownV2"),
}
