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
import org.nirmato.ollama.api.CompletionRequest
import org.nirmato.ollama.api.CompletionResponse
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.client.http.HttpClientHandler
import org.nirmato.ollama.client.http.handle
import org.nirmato.ollama.client.http.handleFlow

public class CompletionsClient(private val requestHandler: HttpClientHandler) : CompletionsApi {
    override suspend fun completion(completionRequest: CompletionRequest): CompletionResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(completionRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun completionFlow(completionRequest: CompletionRequest): Flow<CompletionResponse> {
        return requestHandler.handleFlow<CompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(completionRequest.toStreamRequest(true))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }
}
