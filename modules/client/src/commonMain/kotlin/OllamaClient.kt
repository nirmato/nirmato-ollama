package org.nirmato.ollama.client

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.MonitoringResponse
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.api.VersionResponse
import org.nirmato.ollama.client.http.DefaultHttpClientHandler
import org.nirmato.ollama.client.http.HttpClientHandler
import org.nirmato.ollama.client.http.handle

/**
 * Internal Json Serializer.
 */
internal val JsonLenient: Json = Json {
    isLenient = false
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    classDiscriminatorMode = ClassDiscriminatorMode.NONE
}

/**
 * Implementation of [OllamaApi].
 *
 * @param requestHandler http transport layer
 */
public class OllamaClient(private val requestHandler: HttpClientHandler) : OllamaApi,
    CompletionsApi by CompletionsClient(requestHandler),
    ModelsApi by ModelsClient(requestHandler),
    ChatApi by ChatClient(requestHandler),
    EmbeddingsApi by EmbeddedClient(requestHandler) {

    override suspend fun getVersion(): VersionResponse {
        return requestHandler.handle {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getMonitoring(): MonitoringResponse {
        val builder: HttpRequestBuilder.() -> Unit = {
            method = HttpMethod.Get
            url(path = "version")
            accept(ContentType.Application.Json)
        }

        return flow {
            requestHandler.handle(builder) { httpResponse ->
                emit(MonitoringResponse(httpResponse.status))
            }
        }.single()
    }
}

/**
 * Creates an instance of [OllamaClient].
 */
public fun <T : HttpClientEngineConfig> OllamaClient(
    httpClientEngineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {},
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
