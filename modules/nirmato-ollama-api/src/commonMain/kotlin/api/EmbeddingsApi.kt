package org.nirmato.ollama.api

import org.nirmato.ollama.api.EmbeddingRequest.*

public interface EmbeddingsApi {
    /**
     * Generate embeddings from a model.
     */
    public suspend fun generateEmbedding(generateEmbeddingRequest: EmbeddingRequest): EmbeddingResponse
}

public suspend fun EmbeddingsApi.generateEmbedding(block: EmbeddingRequestBuilder.() -> Unit): EmbeddingResponse {
    val generateEmbeddingRequest = EmbeddingRequestBuilder().apply(block).build()
    return generateEmbedding(generateEmbeddingRequest)
}
