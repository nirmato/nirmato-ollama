package org.nirmato.ollama.client.ktor

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import org.nirmato.ollama.api.MonitoringApi
import org.nirmato.ollama.api.MonitoringResponse
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient

public open class MonitoringClient internal constructor(private val httpClient: KtorHttpClient) : MonitoringApi {
    public override suspend fun getMonitoring(): MonitoringResponse {
        val builder: HttpRequestBuilder.() -> Unit = {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }

        return flow {
            httpClient.perform(builder) { httpResponse ->
                emit(MonitoringResponse(httpResponse.status))
            }
        }.single()
    }
}
