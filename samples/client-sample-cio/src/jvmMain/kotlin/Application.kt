package org.nirmato.ollama.client.samples

import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.cio.CIO
import org.nirmato.ollama.api.ChatCompletionRequest.Companion.chatCompletionRequest
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
import org.nirmato.ollama.client.OllamaClient

fun main() = runBlocking {
    val ollamaClient = OllamaClient(CIO)

    val request = chatCompletionRequest {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    val response = ollamaClient.chatCompletion(request)

    println(response.toString())
}
