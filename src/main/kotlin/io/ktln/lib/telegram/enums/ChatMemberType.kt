package io.ktln.lib.telegram.enums

public final enum class ChatMemberType(
    public final val value: String
) {
    Administrator("administrator"),
    Creator("creator"),
    Kicked("kicked"),
    Left("left"),
    Member("member"),
    Restricted("restricted")
}
