package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.java.Java
import org.nirmato.ollama.api.ChatCompletionRequest.Companion.chatCompletionRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
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
        engine = Java.create()
    }

    val generateCompletionRequest = chatCompletionRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }
    val response = ollama.chatCompletion(generateCompletionRequest)

    println(response.toString())
}
