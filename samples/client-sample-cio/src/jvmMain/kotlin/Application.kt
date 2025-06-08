package org.nirmato.ollama.client.samples

import kotlinx.coroutines.runBlocking
import org.nirmato.ollama.client.ktor.OllamaClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import org.nirmato.ollama.api.ChatRequest.Companion.chatRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER

fun main() = runBlocking {
    val ollamaClient = OllamaClient(CIO) {
        httpClient {
            defaultRequest {
                url("http://localhost:11434/api/")
            }
        }
    }

    val request = chatRequest {
        model("tinyllama")
        messages(listOf(Message(role = USER, content = "Why is the sky blue?")))
    }

    val response = ollamaClient.chat(request)

    println(response.toString())
}
