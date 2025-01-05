package org.nirmato.ollama.client.samples

import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.java.Java
import org.nirmato.ollama.api.ChatRequest.Companion.chatRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
import org.nirmato.ollama.client.OllamaClient

fun main() = runBlocking {
    val ollamaClient = OllamaClient(Java)

    val request = chatRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    val response = ollamaClient.chat(request)

    println(response.toString())
}
