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
import org.nirmato.ollama.client.http.HttpClient
import org.nirmato.ollama.client.http.handleFlow
import org.nirmato.ollama.client.http.perform

public class CompletionsClient(private val httpClient: HttpClient) : CompletionsApi {
    override suspend fun completion(completionRequest: CompletionRequest): CompletionResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(CompletionRequest.builder(completionRequest).apply { stream = false }.build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    override fun completionFlow(completionRequest: CompletionRequest): Flow<CompletionResponse> {
        return httpClient.handleFlow<CompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(CompletionRequest.builder(completionRequest).apply { stream = true }.build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }
}
