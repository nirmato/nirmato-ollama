package org.nirmato.ollama.client

import org.nirmato.ollama.api.ChatApi
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.http.HttpClient

/**
 * Implementation of [OllamaApi].
 *
 * @param httpClient http transport layer
 */
public class OllamaClient(private val httpClient: HttpClient) : OllamaApi {
    private val completions: CompletionsClient by lazy { CompletionsClient(httpClient) }
    private val models: ModelsClient by lazy { ModelsClient(httpClient) }
    private val chat: ChatClient by lazy { ChatClient(httpClient) }
    private val embedded: EmbeddedClient by lazy { EmbeddedClient(httpClient) }
    private val version: VersionClient by lazy { VersionClient(httpClient) }
    private val monitoring: MonitoringClient by lazy { MonitoringClient(httpClient) }

    public override fun chat(): ChatApi = chat
    public override fun completions(): CompletionsClient = completions
    public override fun models(): ModelsClient = models
    public override fun embedded(): EmbeddedClient = embedded
    public override fun version(): VersionClient = version
    public override fun monitoring(): MonitoringClient = monitoring
}
