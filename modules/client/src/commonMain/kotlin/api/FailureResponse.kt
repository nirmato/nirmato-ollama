package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error response from the Ollama API.
 */
@Serializable
public data class FailureResponse(
    /** Information about the error that occurred. */
    @SerialName("error")
    public val message: String? = null,
)
