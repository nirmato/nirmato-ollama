package org.nirmato.ollama.client.ktor

import http.DefaultHttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import org.nirmato.ollama.client.JsonLenient
import org.nirmato.ollama.client.http.HttpClient
import org.nirmato.ollama.client.http.HttpClientFactory
import io.ktor.client.HttpClient as KtorHttpClient

public class KtorHttpClientFactory<T : HttpClientEngineConfig>(
    private val httpClientEngineFactory: HttpClientEngineFactory<T>,
    private val block: HttpClientConfig<T>.() -> Unit = {},
) : HttpClientFactory {

    override fun createHttpClient(): HttpClient {
        val httpClient = KtorHttpClient(httpClientEngineFactory) {
            install(ContentNegotiation) {
                register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
            }

            expectSuccess = true

            block()
        }

        return DefaultHttpClient(httpClient)
    }
}
