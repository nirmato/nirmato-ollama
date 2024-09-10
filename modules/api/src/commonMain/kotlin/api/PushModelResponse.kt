package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response class for pushing a model.
 */
@Serializable
public data class PushModelResponse(

    @SerialName(value = "status")
    val status: PushModelStatus? = null,

    /** the model's digest */
    @SerialName(value = "digest")
    val digest: String? = null,

    /** Total size of the model */
    @SerialName(value = "total")
    val total: Long? = null,

    /** Total bytes transferred. */
    @SerialName(value = "completed")
    val completed: Long? = null,
)
