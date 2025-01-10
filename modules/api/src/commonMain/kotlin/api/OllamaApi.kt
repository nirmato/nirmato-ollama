package org.nirmato.ollama.api

public interface OllamaApi : CompletionsApi, ModelsApi, ChatApi, EmbeddingsApi {
    public suspend fun getVersion(): VersionResponse

    public suspend fun getMonitoring(): MonitoringResponse
}
