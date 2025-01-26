package org.nirmato.ollama.api

public interface VersionApi {
    public suspend fun getVersion(): VersionResponse
}
