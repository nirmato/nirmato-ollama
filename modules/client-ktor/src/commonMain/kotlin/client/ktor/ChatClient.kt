package org.nirmato.ollama.client.ktor

import kotlinx.coroutines.flow.Flow
import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headers
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.ChatRequest
import org.nirmato.ollama.api.ChatResponse
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient
import org.nirmato.ollama.client.ktor.internal.http.handleFlow
import org.nirmato.ollama.client.ktor.internal.http.perform

public open class ChatClient internal constructor(private val httpClient: KtorHttpClient) : ChatApi {
    /**
     * Generate the next message in a chat with a provided model.
     * This is a streaming endpoint, so there will be a series of responses.
     * The final response object will include statistics and additional data from the request.
     */
    public override suspend fun chat(chatRequest: ChatRequest): ChatResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(ChatRequest.builder(chatRequest).stream(false).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * @see #chat
     */
    public override fun chatStream(chatRequest: ChatRequest): Flow<ChatResponse> {
        return httpClient.handleFlow<ChatResponse> {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(ChatRequest.builder(chatRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }
}
