package org.nirmato.ollama.client.http

import io.ktor.client.HttpClient

public interface HttpClientProvider {
    public fun buildClient(): HttpClient
}
