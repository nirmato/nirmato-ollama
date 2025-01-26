package org.nirmato.ollama.client

import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.nirmato.ollama.api.EmbeddedRequest
import org.nirmato.ollama.api.EmbeddedResponse
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.client.http.HttpClient
import org.nirmato.ollama.client.http.perform

public class EmbeddedClient(private val httpClient: HttpClient) : EmbeddingsApi {
    override suspend fun generateEmbedded(generateEmbeddedRequest: EmbeddedRequest): EmbeddedResponse {
        return httpClient.perform {
            method = HttpMethod.Post
            url(path = "embed")
            setBody(generateEmbeddedRequest)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
