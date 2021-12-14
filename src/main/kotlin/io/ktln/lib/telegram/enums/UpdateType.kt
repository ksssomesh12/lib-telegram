package io.ktln.lib.telegram.enums

public final enum class UpdateType(
    public final val value: String
) {
    CallbackQuery("callback_query"),
    ChannelPost("channel_post"),
    ChatMember("chat_member"),
    ChatJoinRequest("chat_join_request"),
    ChosenInlineRequest("chosen_inline_result"),
    EditedChannelPost("edited_channel_post"),
    EditedMessage("edited_message"),
    InlineQuery("inline_query"),
    Message("message"),
    MyChatMember("my_chat_member"),
    Poll("poll"),
    PollAnswer("poll_answer"),
    PreCheckoutQuery("pre_checkout_query"),
    ShippingQuery("shipping_query")
}
