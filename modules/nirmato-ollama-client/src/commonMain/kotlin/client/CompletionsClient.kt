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
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.GenerateCompletionRequest
import org.nirmato.ollama.api.GenerateCompletionResponse
import org.nirmato.ollama.internal.HttpRequester
import org.nirmato.ollama.internal.JsonLenient
import org.nirmato.ollama.internal.processRequest
import org.nirmato.ollama.internal.streamRequest

internal class CompletionsClient internal constructor(private val requester: HttpRequester) : CompletionsApi {
    override suspend fun generateCompletion(generateCompletionRequest: GenerateCompletionRequest): GenerateCompletionResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(generateCompletionRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun generateCompletions(generateCompletionRequest: GenerateCompletionRequest): Flow<GenerateCompletionResponse> {
        return requester.streamRequest<GenerateCompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(generateCompletionRequest.toStreamRequest())
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
    private fun GenerateCompletionRequest.toStreamRequest(): JsonElement {
        val json = JsonLenient.encodeToJsonElement(GenerateCompletionRequest.serializer(), this)
        return streamRequestOf(json)
    }

    private inline fun <reified T> streamRequestOf(serializable: T): JsonElement {
        val enableStream = "stream" to JsonPrimitive(true)
        val json = JsonLenient.encodeToJsonElement(serializable)
        val map = json.jsonObject.toMutableMap().also { it += enableStream }
        return JsonObject(map)
    }
}
