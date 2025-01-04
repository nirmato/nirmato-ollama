package org.nirmato.ollama.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.http.DefaultHttpClientHandler
import org.nirmato.ollama.client.http.HttpClientHandler

/**
 * Implementation of [OllamaApi].
 *
 * @param requestHandler http transport layer
 */
public class OllamaClient(private val requestHandler: HttpClientHandler) : OllamaApi,
    CompletionsApi by CompletionsClient(requestHandler),
    ModelsApi by ModelsClient(requestHandler),
    ChatApi by ChatClient(requestHandler),
    EmbeddingsApi by EmbeddedClient(requestHandler)

/**
 * Creates an instance of [OllamaClient].
 */
public fun <T: HttpClientEngineConfig> OllamaClient(
    httpClientEngineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {}
): OllamaClient {
    val client = HttpClient(httpClientEngineFactory) {
        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
        }

        expectSuccess = true

        block()
    }

    val requestHandler = DefaultHttpClientHandler(client)

    return OllamaClient(requestHandler)
}
