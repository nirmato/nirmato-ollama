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
import org.nirmato.ollama.client.http.RequestHandler
import org.nirmato.ollama.client.http.handle

public class EmbeddedClient(private val requestHandler: RequestHandler) : EmbeddingsApi {
    override suspend fun generateEmbedding(generateEmbeddingRequest: EmbeddingRequest): EmbeddingResponse {
        return requestHandler.handle {
            method = HttpMethod.Post
            url(path = "embed")
            setBody(generateEmbeddingRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
