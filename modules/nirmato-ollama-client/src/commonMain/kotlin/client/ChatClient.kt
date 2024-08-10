package org.nirmato.ollama.client

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.GenerateChatRequest
import org.nirmato.ollama.api.GenerateChatResponse
import org.nirmato.ollama.internal.HttpRequester
import org.nirmato.ollama.internal.JsonLenient
import org.nirmato.ollama.internal.processRequest
import org.nirmato.ollama.internal.processRequestFlow

internal class ChatClient internal constructor(private val requester: HttpRequester) : ChatApi {
    override suspend fun generateChat(generateChatRequest: GenerateChatRequest): GenerateChatResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(generateChatRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun generateChatFlow(generateChatRequest: GenerateChatRequest): Flow<GenerateChatResponse> {
        return requester.processRequestFlow<GenerateChatResponse> {
            method = HttpMethod.Post
            url(path = "chat")
            setBody(generateChatRequest.toStreamRequest())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    /**
     * Adds `stream` parameter to the request.
     */
    private fun GenerateChatRequest.toStreamRequest(): JsonElement {
        val json = JsonLenient.encodeToJsonElement(GenerateChatRequest.serializer(), this)
        return streamRequestOf(json)
    }

    private inline fun <reified T> streamRequestOf(serializable: T): JsonElement {
        val enableStream = "stream" to JsonPrimitive(true)
        val json = JsonLenient.encodeToJsonElement(serializable)
        val map = json.jsonObject.toMutableMap().also { it += enableStream }
        return JsonObject(map)
    }
}
