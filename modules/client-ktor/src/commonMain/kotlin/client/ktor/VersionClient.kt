package org.nirmato.ollama.client.ktor

import io.ktor.client.request.accept
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import org.nirmato.ollama.api.VersionApi
import org.nirmato.ollama.api.VersionResponse
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient
import org.nirmato.ollama.client.ktor.internal.http.perform

public open class VersionClient internal constructor(private val httpClient: KtorHttpClient) : VersionApi {
    public override suspend fun getVersion(): VersionResponse {
        return httpClient.perform {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }
    }
}
