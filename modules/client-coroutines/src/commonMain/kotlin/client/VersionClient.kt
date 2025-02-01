package org.nirmato.ollama.client

import io.ktor.client.request.accept
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import org.nirmato.ollama.api.VersionApi
import org.nirmato.ollama.api.VersionResponse
import org.nirmato.ollama.client.http.HttpClient
import org.nirmato.ollama.client.http.perform

public class VersionClient(private val httpClient: HttpClient) : VersionApi {
    override suspend fun getVersion(): VersionResponse {
        return httpClient.perform {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }
    }
}
