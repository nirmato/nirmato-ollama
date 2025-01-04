package org.nirmato.ollama.client.samples

import io.ktor.client.engine.js.Js
import org.nirmato.ollama.api.ChatCompletionRequest.Companion.chatCompletionRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.client.OllamaClient
import org.nirmato.ollama.api.Role.USER

suspend fun main() {
    val ollamaClient = OllamaClient(Js)

    val request = chatCompletionRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    val response = ollamaClient.chatCompletion(request)

    println(response.toString())
}
