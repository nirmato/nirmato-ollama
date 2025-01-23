package org.nirmato.ollama.client.samples

import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.defaultRequest
import org.nirmato.ollama.api.ChatRequest.Companion.chatRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
import org.nirmato.ollama.client.OllamaClient

suspend fun main() {
    val ollamaClient = OllamaClient(Js) {
        defaultRequest {
            url("http://localhost:11434/api/")
        }
    }

    val request = chatRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    val response = ollamaClient.chat(request)

    println(response.toString())
}
