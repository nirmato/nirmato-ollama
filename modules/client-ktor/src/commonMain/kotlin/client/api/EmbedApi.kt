package org.nirmato.ollama.api

public interface EmbedApi {
    /**
     * Generate embeddings from a model.
     */
    public suspend fun generateEmbed(generateEmbeddedRequest: EmbeddedRequest): EmbeddedResponse
}
