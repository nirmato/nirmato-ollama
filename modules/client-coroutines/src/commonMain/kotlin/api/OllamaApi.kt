package org.nirmato.ollama.api

public interface OllamaApi {
    public fun completions(): CompletionsApi
    public fun models(): ModelsApi
    public fun chat(): ChatApi
    public fun embedded(): EmbeddedApi
    public fun version(): VersionApi
    public fun monitoring(): MonitoringApi
}
