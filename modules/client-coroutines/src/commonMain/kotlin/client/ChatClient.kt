package org.nirmato.ollama.client

import kotlinx.coroutines.flow.Flow
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.ChatRequest
import org.nirmato.ollama.api.ChatResponse
import org.nirmato.ollama.client.http.HttpClient
import org.nirmato.ollama.client.http.handleFlow
import org.nirmato.ollama.client.http.perform

public class ChatClient(private val httpClient: HttpClient) : ChatApi {
    override suspend fun chat(chatRequest: ChatRequest): ChatResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(ChatRequest.builder(chatRequest).apply { stream = false }.build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun chatFlow(chatRequest: ChatRequest): Flow<ChatResponse> {
        return httpClient.handleFlow<ChatResponse> {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(ChatRequest.builder(chatRequest).apply { stream = true }.build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }
}
