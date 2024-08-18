package org.nirmato.ollama.client

import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.EmbeddingRequest
import org.nirmato.ollama.api.EmbeddingResponse
import org.nirmato.ollama.internal.HttpRequester
import org.nirmato.ollama.internal.processRequest

internal class EmbeddedClient internal constructor(private val requester: HttpRequester) : EmbeddingsApi {
    override suspend fun generateEmbedding(generateEmbeddingRequest: EmbeddingRequest): EmbeddingResponse {
        return requester.processRequest {
            method = HttpMethod.Post
            url(path = "embed")
            setBody(generateEmbeddingRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
