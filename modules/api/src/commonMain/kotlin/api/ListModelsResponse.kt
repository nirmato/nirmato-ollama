package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response class for the list models endpoint.
 */
@Serializable
public data class ListModelsResponse(

    /** List of models available locally. */
    @SerialName(value = "models")
    val models: List<OllamaModel>? = null,
)
