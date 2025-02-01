package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response object for creating a model. When finished, `status` is `success`.
 */
@Serializable
public data class CreateModelResponse(
    @SerialName(value = "status")
    val status: String? = null,
)

