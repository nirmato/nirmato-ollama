package org.nirmato.ollama.client.http

import kotlin.time.DurationUnit
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
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
import org.nirmato.ollama.client.ProxyConfig

public open class DefaultHttpClientProvider(private val engine: HttpClientEngine, private val config: OllamaConfig) : HttpClientProvider {

    public open fun buildEngine(): HttpClientEngine = engine

    override fun buildClient(): HttpClient = HttpClient(buildEngine()) {
        engine {
            config.proxy?.let { proxyConfig ->
                proxy = when (proxyConfig) {
                    is ProxyConfig.Http -> ProxyBuilder.http(proxyConfig.url)
                    is ProxyConfig.Socks -> ProxyBuilder.socks(proxyConfig.host, proxyConfig.port)
                }
            }
        }

        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
        }

        install(Logging) {
            logger = config.logging.logger.toKtorLogger()
            level = config.logging.logLevel.toKtorLogLevel()

            if (config.logging.sanitize) {
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
            retryIf { _, response -> response.status.value.let { it == HttpStatusCode.Companion.TooManyRequests.value } }
            exponentialDelay(config.retry.base, config.retry.maxDelay.inWholeMilliseconds)
        }

        defaultRequest {
            url(config.host.baseUrl)
            config.headers.onEach { (key, value) -> headers.appendIfNameAbsent(key, value) }
        }

        expectSuccess = true
    }
}
