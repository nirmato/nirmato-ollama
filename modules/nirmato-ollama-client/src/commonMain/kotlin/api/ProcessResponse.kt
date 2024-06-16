package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response class for the list of models running.
 */
@Serializable
public data class ProcessResponse(

    /** List of running models. */
    @SerialName(value = "models")
    val models: List<ProcessModel>? = null,
)
