package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.java.Java
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
import org.nirmato.ollama.api.chatCompletion
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaClient
import org.nirmato.ollama.client.OllamaConfigBuilder
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.RetryStrategy
import org.nirmato.ollama.client.TimeoutConfig
import org.nirmato.ollama.client.http.DefaultHttpClientProvider

fun main() = runBlocking {
    val ollamaConfig = OllamaConfigBuilder().apply {
        logging = LoggingConfig(logLevel = LogLevel.All)
        timeout = TimeoutConfig(socket = 30.seconds)
        host = OllamaHost.Local
        retry = RetryStrategy(0)
    }.build()

    val httpClientProvider = DefaultHttpClientProvider(Java.create(), ollamaConfig)
    val ollamaClient = OllamaClient(httpClientProvider)

    val response = ollamaClient.chatCompletion {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    println(response.toString())
}
