package io.ktln.lib.telegram

import io.ktln.lib.telegram.api.Chat
import io.ktln.lib.telegram.api.User
import io.ktln.lib.telegram.ext.Bot
import io.ktln.lib.telegram.internal.convertKeysToCamelCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal final class BotTest(
) {
    private final val bot: Bot = Bot(
        baseUrl = "http://localhost:8081/bot",
        token = "5011757897:AAHmzOL7s_t-Pu7tu1JwaNYhb0vR1qWplzQ"
    )

    @Test
    public final fun getMe() {
        assertEquals(
            runBlocking {
                bot.getMe(
                    timeOut = 5000.0f
                )
            },
            User.fromDataMap(
                dataMap = mapOf(
                    "id" to 5011757897,
                    "is_bot" to true,
                    "first_name" to "test",
                    "username" to "TestTgmbBot",
                    "can_join_groups" to true,
                    "can_read_all_group_messages" to false,
                    "supports_inline_queries" to false
                ).convertKeysToCamelCase()
            )
        )
    }

    @Test
    public fun getChat() {
        assertEquals(
            runBlocking {
                bot.getChat(
                    chatId = -599746506,
                    timeOut = 5000.0f
                )
            },
            Chat.fromDataMap(
                dataMap = mapOf(
                    "id" to -599746506,
                    "title" to "test-group",
                    "type" to "group",
                    "permissions" to mapOf(
                        "can_send_messages" to true,
                        "can_send_media_messages" to true,
                        "can_send_polls" to true,
                        "can_send_other_messages" to true,
                        "can_add_web_page_previews" to true,
                        "can_change_info" to true,
                        "can_invite_users" to true,
                        "can_pin_messages" to true
                    ).convertKeysToCamelCase(),
                    "all_members_are_administrators" to true
                ).convertKeysToCamelCase()
            )
        )
    }
}
