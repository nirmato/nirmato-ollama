package org.nirmato.ollama.client.samples

import kotlinx.coroutines.runBlocking
import io.ktor.client.engine.java.Java
import org.nirmato.ollama.api.Message
import org.nirmato.ollama.api.Role.USER
import org.nirmato.ollama.api.chatCompletion
import org.nirmato.ollama.client.OllamaClient

fun main() = runBlocking {
    val ollamaClient = OllamaClient(Java)

    val response = ollamaClient.chatCompletion {
        model = "tinyllama"
        messages = listOf(Message(role = USER, content = "Why is the sky blue?"))
    }

    println(response.toString())
}
