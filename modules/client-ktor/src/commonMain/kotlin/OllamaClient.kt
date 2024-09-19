package org.nirmato.ollama.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.http.KtorHttpClientHandler
import org.nirmato.ollama.client.http.HttpClientHandler
import org.nirmato.ollama.client.http.OllamaHttpClient

/**
 * Implementation of [OllamaApi].
 *
 * @param requestHandler http transport layer
 */
public class OllamaClient internal constructor(private val requestHandler: HttpClientHandler) : OllamaApi,
    CompletionsApi by CompletionsClient(requestHandler),
    ModelsApi by ModelsClient(requestHandler),
    ChatApi by ChatClient(requestHandler),
    EmbeddingsApi by EmbeddedClient(requestHandler)

/**
 * Creates an instance of [OllamaClient].
 */
public fun OllamaClient(
    config: OllamaConfig,
    engine: HttpClientEngine,
    httpClientConfig: HttpClientConfig<*>.() -> Unit = {},
): OllamaClient {
    val httpClient = OllamaHttpClient(config, engine, httpClientConfig)
    val requestHandler = KtorHttpClientHandler(httpClient.client)
    return OllamaClient(requestHandler)
}

/**
 * Creates an instance of [OllamaClient].
 */
public fun OllamaClient(
    config: OllamaConfig,
    httpClientConfig: HttpClientConfig<*>.() -> Unit = {},
): OllamaClient {
    val httpClient = OllamaHttpClient(config, httpClientConfig)
    val requestHandler = KtorHttpClientHandler(httpClient.client)
    return OllamaClient(requestHandler)
}

/**
 * Creates an instance of [OllamaApi].
 */
public fun OllamaClient(block: OllamaConfigBuilder.() -> Unit): OllamaClient {
    val config = OllamaConfigBuilder().apply(block).build()

    return OllamaClient(config)
}
