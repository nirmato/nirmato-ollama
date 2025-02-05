package org.nirmato.ollama.client.ktor

import client.api.OllamaApi
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbedApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.MonitoringApi
import org.nirmato.ollama.api.VersionApi
import org.nirmato.ollama.client.ktor.internal.http.JsonLenient
import org.nirmato.ollama.client.ktor.internal.http.KtorHttpClient

/**
 * Implementation of [OllamaClient].
 *
 * @param httpClient http transport layer
 */
public class OllamaClient private constructor(private val httpClient: KtorHttpClient) : OllamaApi,
    ChatApi by ChatClient(httpClient),
    CompletionsApi by CompletionsClient(httpClient),
    EmbedApi by EmbedClient(httpClient),
    ModelsApi by ModelsClient(httpClient),
    MonitoringApi by MonitoringClient(httpClient),
    VersionApi by VersionClient(httpClient) {

    public companion object {
        public operator fun <T : HttpClientEngineConfig> invoke(
            httpClientEngineFactory: HttpClientEngineFactory<T>,
            block: HttpClientConfig<T>.() -> Unit = {},
        ): OllamaClient {
            val httpClient = HttpClient(httpClientEngineFactory) {
                install(ContentNegotiation) {
                    register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
                }

                expectSuccess = true

                block()
            }

            return OllamaClient(KtorHttpClient(httpClient))
        }
    }
}
