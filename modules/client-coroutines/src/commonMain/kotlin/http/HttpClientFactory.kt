package org.nirmato.ollama.client.http

public interface HttpClientFactory {
    public fun createHttpClient(): HttpClient
}
