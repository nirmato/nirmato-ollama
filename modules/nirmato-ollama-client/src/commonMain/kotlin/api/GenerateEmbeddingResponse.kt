package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Returns the embedding information.
 */
@Serializable
public data class GenerateEmbeddingResponse(

    /** The embedding for the prompt. */
    @SerialName(value = "embedding")
    val embedding: List<Double>? = null,
)
