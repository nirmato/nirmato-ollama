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
import org.nirmato.ollama.api.CompletionRequest
import org.nirmato.ollama.api.CompletionResponse
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.client.http.RequestHandler
import org.nirmato.ollama.client.http.handle
import org.nirmato.ollama.client.http.handleFlow

public class CompletionsClient(private val requestHandler: RequestHandler) : CompletionsApi {
    override suspend fun completion(completionRequest: CompletionRequest): CompletionResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(completionRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun completionFlow(completionRequest: CompletionRequest): Flow<CompletionResponse> {
        return requestHandler.handleFlow<CompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(completionRequest.toStreamRequest())
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
    private fun CompletionRequest.toStreamRequest(): JsonElement {
        val json = JsonLenient.encodeToJsonElement(CompletionRequest.serializer(), this)
        return streamRequestOf(json)
    }

    private inline fun <reified T> streamRequestOf(serializable: T): JsonElement {
        val enableStream = "stream" to JsonPrimitive(true)
        val json = JsonLenient.encodeToJsonElement(serializable)
        val map = json.jsonObject.toMutableMap().also { it += enableStream }
        return JsonObject(map)
    }
}
