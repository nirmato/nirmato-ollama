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
import org.nirmato.ollama.api.CompletionRequest
import org.nirmato.ollama.api.CompletionResponse
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient
import org.nirmato.ollama.client.ktor.internal.http.handleFlow
import org.nirmato.ollama.client.ktor.internal.http.perform

public open class CompletionsClient internal constructor(private val httpClient: KtorHttpClient) : CompletionsApi {
    /**
     * Generate a response for a given prompt with a provided model.
     * This is a streaming endpoint, so there will be a series of responses. The final response object will include statistics and additional data from the request.
     */
    public override suspend fun completion(completionRequest: CompletionRequest): CompletionResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(CompletionRequest.builder(completionRequest).stream(false).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    /**
     * @see #completion
     */
    public override fun completionStream(completionRequest: CompletionRequest): Flow<CompletionResponse> {
        return httpClient.handleFlow<CompletionResponse> {
            method = HttpMethod.Post
            url(path = "generate")
            setBody(CompletionRequest.builder(completionRequest).stream(true).build())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }
}
