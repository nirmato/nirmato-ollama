package org.nirmato.ollama.client.samples

import kotlin.time.Duration.Companion.seconds
import io.ktor.client.engine.js.Js
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role
import org.nirmato.ollama.api.chatCompletion
import org.nirmato.ollama.client.LogLevel
import org.nirmato.ollama.client.LoggingConfig
import org.nirmato.ollama.client.OllamaClient
import org.nirmato.ollama.client.OllamaHost
import org.nirmato.ollama.client.http.DefaultHttpClientProvider

suspend fun main() {
    val ollamaClient = OllamaClient(Js)

    val response = ollamaClient.chatCompletion {
        model = "tinyllama"
        messages = listOf(Message(role = Role.USER, content = "Why is the sky blue?"))
    }

    println(response.toString())
}
