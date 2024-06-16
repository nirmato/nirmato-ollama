package org.nirmato.ollama

import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.OllamaClient
import org.nirmato.ollama.internal.HttpTransport
import org.nirmato.ollama.internal.createHttpClient
import org.nirmato.ollama.client.OllamaConfig
import org.nirmato.ollama.client.OllamaConfigBuilder

/**
 * Creates an instance of [OllamaApi].
 */
public fun createOllamaClient(config: OllamaConfig): OllamaApi {
    val httpClient = createHttpClient(config)
    val transporter = HttpTransport(httpClient)
    return OllamaClient(transporter)
}

/**
 * Creates an instance of [OllamaApi].
 */
public fun createOllamaClient(block: OllamaConfigBuilder.() -> Unit): OllamaApi {
    val config = OllamaConfigBuilder().apply(block).build()

    return createOllamaClient(config)
}
