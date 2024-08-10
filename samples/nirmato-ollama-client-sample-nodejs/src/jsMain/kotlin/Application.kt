package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import io.ktor.client.engine.js.Js
import org.nirmato.ollama.api.GenerateChatRequest.Companion.generateChatRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Message.Role.USER
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.RetryStrategy
import org.nirmato.ollama.client.TimeoutConfig
import org.nirmato.ollama.createOllamaClient

suspend fun main() {
    val ollama = createOllamaClient {
        logging = LoggingConfig(logLevel = LogLevel.All)
        timeout = TimeoutConfig(socket = 30.seconds)
        host = OllamaHost.Local
        retry = RetryStrategy(maxRetries = 0)
        engine = Js.create()
    }

    val generateCompletionRequest = generateChatRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }
    val response = ollama.generateChat(generateCompletionRequest)

    println(response.toString())
}
