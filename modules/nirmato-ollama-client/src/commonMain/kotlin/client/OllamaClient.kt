package org.nirmato.ollama.client

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.internal.RequestHandler
import org.nirmato.ollama.client.internal.KtorRequestHandler
import org.nirmato.ollama.client.internal.createHttpClient

/**
 * Implementation of [OllamaApi].
 *
 * @param requestHandler http transport layer
 */
public class OllamaClient internal constructor(private val requestHandler: RequestHandler) : OllamaApi,
    CompletionsApi by CompletionsClient(requestHandler),
    ModelsApi by ModelsClient(requestHandler),
    ChatApi by ChatClient(requestHandler),
    EmbeddingsApi by EmbeddedClient(requestHandler)

/**
 * Creates an instance of [OllamaApi].
 */
public fun OllamaClient(config: OllamaConfig): OllamaClient {
    val httpClient = createHttpClient(config)
    val requestHandler = KtorRequestHandler(httpClient)
    return OllamaClient(requestHandler)
}

/**
 * Creates an instance of [OllamaApi].
 */
public fun OllamaClient(block: OllamaConfigBuilder.() -> Unit): OllamaClient {
    val config = OllamaConfigBuilder().apply(block).build()

    return OllamaClient(config)
}
