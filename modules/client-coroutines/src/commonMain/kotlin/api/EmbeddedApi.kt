package org.nirmato.ollama.api

public interface EmbeddedApi {
    /**
     * Generate embeddings from a model.
     */
    public suspend fun generateEmbedded(generateEmbeddedRequest: EmbeddedRequest): EmbeddedResponse
}
