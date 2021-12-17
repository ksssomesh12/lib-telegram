package io.ktln.lib.telegram

import io.ktln.lib.telegram.enums.ParseMode
import io.ktln.lib.telegram.ext.Bot
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        Bot(
            baseUrl = "http://localhost:8081/bot",
            token = "5011757897:AAHmzOL7s_t-Pu7tu1JwaNYhb0vR1qWplzQ"
        ).apply {
            println(getMe())
            println(getChat(-599746506))
            for (update in getUpdates()) { println(update) }
            val originalMsg = sendMessage(642471388, "Hi !", ParseMode.HTML).setBot(this)
            println(originalMsg)
            delay(3000)
            val editedMsg = originalMsg.editMessageText("Waiting...").setBot(this)
            println(editedMsg)
            delay(3000)
            println(editedMsg.deleteMessage())
            println(getUserProfilePhotos(642471388))
            println(this)
        }
    }
}
