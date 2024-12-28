package org.nirmato.ollama.client

import org.nirmato.ollama.client.http.DefaultHttpClientHandler
import org.nirmato.ollama.client.http.HttpClientProvider
import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.CompletionsApi
import org.nirmato.ollama.api.EmbeddingsApi
import org.nirmato.ollama.api.ModelsApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.http.HttpClientHandler

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
    httpClientProvider: HttpClientProvider,
): OllamaClient {
    val requestHandler = DefaultHttpClientHandler(httpClientProvider.buildClient())
    return OllamaClient(requestHandler)
}
