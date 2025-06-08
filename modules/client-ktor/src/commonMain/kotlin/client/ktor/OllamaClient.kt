package org.nirmato.ollama.client.ktor

import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import org.nirmato.ollama.api.OllamaApi

/**
 * Client for interacting with the Ollama API.
 *
 * This client provides methods for all Ollama API endpoints.
 */
public class OllamaClient(
    private val httpClient: HttpClient,
    private val clientConfig: OllamaClientConfig,
) : OllamaApi by OllamaService(
    httpTransport = HttpTransport(httpClient, clientConfig)
), AutoCloseable {

    /**
     * Builder for configuring and creating OllamaClient instances.
     *
     * @param httpClientEngineFactory The ktor HTTP client engine factory to use for the client.
     */
    public open class Builder<T : HttpClientEngineConfig> internal constructor(
        private var httpClientEngineFactory: HttpClientEngineFactory<T>,
    ) {
        /**
         * Base URL for the Ollama API.
         */
        protected var ollamaBaseUrl: String = "http://localhost:11434/api/"

        /**
         * JSON configuration for serialization and deserialization.
         */
        protected var jsonConfig: Json = Json {
            classDiscriminatorMode = ClassDiscriminatorMode.NONE
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = false
        }

        protected var client: HttpClient? = null

        protected var httpClientConfig: HttpClientConfig<T>.() -> Unit = {}

        /**
         * Configures the JSON serialization and deserialization configuration.
         *
         * Example:
         * ```kotlin
         * val client = OllamaClient {
         *     jsonConfig {
         *         prettyPrint = false
         *         ignoreUnknownKeys = true
         *     }
         * }
         * ```
         *
         * @param block Configuration block for JSON configuration.
         * @return This builder for chaining
         */
        public fun jsonConfig(block: Json.() -> Unit) {
            jsonConfig = jsonConfig.apply(block)
        }

        public fun jsonConfig(json: Json) {
            jsonConfig = json
        }

        /**
         * Configures the underlying HTTP client with custom settings.
         *
         * Use this method for advanced customization of the HTTP client.
         *
         * Example:
         * ```kotlin
         * val client = OllamaClient(CIO) {
         *     httpClient {
         *         install(HttpTimeout) {
         *             requestTimeoutMillis = 60000
         *         }
         *     }
         * }
         * ```
         *
         * @param httpClientConfig Configuration block for the HTTP client
         * @return This builder for chaining
         */
        public fun httpClient(httpClientConfig: HttpClientConfig<T>.() -> Unit) {
            this.httpClientConfig = httpClientConfig
        }

        public fun httpClient(client: HttpClient) {
            this.client = client
        }

        /**
         * Builds and returns a new OllamaClient instance with the configured settings.
         *
         * @return A new OllamaClient instance
         */
        public fun build(): OllamaClient = OllamaClient(
            httpClient = client ?: HttpClient(httpClientEngineFactory) {
                defaultRequest {
                    url(ollamaBaseUrl)
                    contentType(ContentType.Application.Json)
                }

                install(ContentNegotiation) {
                    json(jsonConfig)
                }

                expectSuccess = true

                httpClientConfig(this)
            },

            clientConfig = OllamaClientConfig(jsonConfig),
        )
    }

    /**
     * Closes the underlying HTTP client and releases resources.
     */
    override fun close() {
        httpClient.close()
    }

    public companion object {
        public operator fun <T : HttpClientEngineConfig> invoke(httpClientEngineFactory: HttpClientEngineFactory<T>, block: Builder<T>.() -> Unit = {}): OllamaClient =
            Builder(httpClientEngineFactory).apply(block).build()
    }
}
