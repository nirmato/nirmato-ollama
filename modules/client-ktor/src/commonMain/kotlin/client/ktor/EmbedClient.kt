package org.nirmato.ollama.client.ktor

import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.nirmato.ollama.api.EmbedApi
import org.nirmato.ollama.api.EmbedRequest
import org.nirmato.ollama.api.EmbedResponse
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient
import org.nirmato.ollama.client.ktor.internal.http.perform

public open class EmbedClient internal constructor(private val httpClient: KtorHttpClient) : EmbedApi {
    /**
     * Generate embeddings from a model.
     */
    public override suspend fun generateEmbed(generateEmbedRequest: EmbedRequest): EmbedResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "embed")
            setBody(generateEmbedRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
