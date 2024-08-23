package org.nirmato.ollama.client

import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.internal.HttpRequester
import org.nirmato.ollama.client.internal.HttpTransport
import org.nirmato.ollama.client.internal.createHttpClient

/**
 * Implementation of [OllamaApi].
 *
 * @param requester http transport layer
 */
internal class OllamaClient internal constructor(private val requester: HttpRequester) : OllamaApi,
    CompletionsApi by CompletionsClient(requester),
    ModelsApi by ModelsClient(requester),
    ChatApi by ChatClient(requester),
    EmbeddingsApi by EmbeddedClient(requester)

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
