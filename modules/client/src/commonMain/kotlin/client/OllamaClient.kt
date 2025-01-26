package org.nirmato.ollama.client

import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import org.nirmato.ollama.api.OllamaApi
import org.nirmato.ollama.client.http.HttpClient

/**
 * Internal Json Serializer.
 */
public val JsonLenient: Json = Json {
    isLenient = false
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    classDiscriminatorMode = ClassDiscriminatorMode.NONE
}

/**
 * Implementation of [OllamaApi].
 *
 * @param httpClient http transport layer
 */
public class OllamaClient(private val httpClient: HttpClient) : OllamaApi {
    public val completions: CompletionsClient by lazy { CompletionsClient(httpClient) }
    public val models: ModelsClient by lazy { ModelsClient(httpClient) }
    public val chat: ChatClient by lazy { ChatClient(httpClient) }
    public val embedded: EmbeddedClient by lazy { EmbeddedClient(httpClient) }
    public val version: VersionClient by lazy { VersionClient(httpClient) }
    public val monitoring: MonitoringClient by lazy { MonitoringClient(httpClient) }
}
