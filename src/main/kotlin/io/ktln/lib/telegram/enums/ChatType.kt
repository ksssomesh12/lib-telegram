package io.ktln.lib.telegram.enums

public final enum class ChatType(
    public final val value: String
) {
    Channel("channel"),
    Group("group"),
    Sender("sender"),
    Private("private"),
    Supergroup("supergroup")
}
