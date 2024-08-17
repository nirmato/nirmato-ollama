package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.cio.CIO
import org.nirmato.ollama.api.GenerateChatCompletionRequest.Companion.generateChatCompletionRequest
import org.nirmato.ollama.api.MessageRequest
import org.nirmato.ollama.api.MessageRequest.Role.USER
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.RetryStrategy
import org.nirmato.ollama.client.TimeoutConfig
import org.nirmato.ollama.createOllamaClient

fun main() = runBlocking {
    val ollama = createOllamaClient {
        logging = LoggingConfig(logLevel = LogLevel.All)
        timeout = TimeoutConfig(socket = 30.seconds)
        host = OllamaHost.Local
        retry = RetryStrategy(0)
        engine = CIO.create()
    }

    val generateCompletionRequest = generateChatCompletionRequest {
        model = "tinyllama"
        messages = listOf(MessageRequest(role = USER, content = "Why is the sky blue?"))
    }
    val response = ollama.generateChatCompletion(generateCompletionRequest)

    println(response.toString())
}
