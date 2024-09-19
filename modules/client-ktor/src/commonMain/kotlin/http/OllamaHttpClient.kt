package org.nirmato.ollama.client.http

import kotlin.time.DurationUnit
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.util.appendIfNameAbsent
import org.nirmato.ollama.client.JsonLenient
import org.nirmato.ollama.client.OllamaConfig
import org.nirmato.ollama.client.ProxyConfig.Http
import org.nirmato.ollama.client.ProxyConfig.Socks

public class OllamaHttpClient(public val client: HttpClient) {

    public constructor(
        config: OllamaConfig,
        engine: HttpClientEngine,
        httpClientConfig: HttpClientConfig<*>.() -> Unit = {},
    ) : this(
        HttpClient(engine) {
            configure(config)
            httpClientConfig()
        }
    )

    public constructor(
        config: OllamaConfig,
        httpClientConfig: HttpClientConfig<*>.() -> Unit = {},
    ) : this(
        HttpClient {
            configure(config)
            httpClientConfig()
        }
    )
}

private fun HttpClientConfig<*>.configure(config: OllamaConfig) {
    engine {
        config.proxy?.let { proxyConfig ->
            proxy = when (proxyConfig) {
                is Http -> ProxyBuilder.http(proxyConfig.url)
                is Socks -> ProxyBuilder.socks(proxyConfig.host, proxyConfig.port)
            }
        }
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
    }

    install(Logging) {
        val logging = config.logging
        logger = logging.logger.toKtorLogger()
        level = logging.logLevel.toKtorLogLevel()
        if (logging.sanitize) {
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    install(HttpTimeout) {
        config.timeout.socket?.let { socketTimeout ->
            socketTimeoutMillis = socketTimeout.toLong(DurationUnit.MILLISECONDS)
        }
        config.timeout.connect?.let { connectTimeout ->
            connectTimeoutMillis = connectTimeout.toLong(DurationUnit.MILLISECONDS)
        }
        config.timeout.request?.let { requestTimeout ->
            requestTimeoutMillis = requestTimeout.toLong(DurationUnit.MILLISECONDS)
        }
    }

    install(HttpRequestRetry) {
        maxRetries = config.retry.maxRetries
        retryIf { _, response -> response.status.value.let { it == HttpStatusCode.TooManyRequests.value } }
        exponentialDelay(config.retry.base, config.retry.maxDelay.inWholeMilliseconds)
    }

    defaultRequest {
        url(config.host.baseUrl)
        config.headers.onEach { (key, value) -> headers.appendIfNameAbsent(key, value) }
    }

    expectSuccess = true
}

/**
 * Creates an asynchronous [OllamaHttpClient] with the specified [HttpClientEngineFactory] and optional [block] configuration.
 * Note that a specific platform may require a specific engine for processing requests.
 *
 * See https://ktor.io/docs/http-client-engines.html
 */
public fun <T : HttpClientEngineConfig> OllamaHttpClient(
    engine: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {},
): OllamaHttpClient = OllamaHttpClient(HttpClient(engine, block))

/**
 * Returns a new [OllamaHttpClient] by copying this client's configuration and additionally configured by the [block] parameter.
 */
public fun OllamaHttpClient.config(block: HttpClientConfig<*>.() -> Unit): OllamaHttpClient {
    return OllamaHttpClient(client.config(block))
}
