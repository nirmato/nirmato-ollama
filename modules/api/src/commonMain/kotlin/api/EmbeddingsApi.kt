package org.nirmato.ollama.api

public interface EmbeddingsApi {
    /**
     * Generate embeddings from a model.
     */
    public suspend fun generateEmbedded(generateEmbeddedRequest: EmbeddedRequest): EmbeddedResponse
}
