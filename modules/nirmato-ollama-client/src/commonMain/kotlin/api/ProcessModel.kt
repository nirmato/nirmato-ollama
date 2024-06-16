package org.nirmato.ollama.api

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A model that is currently loaded.
 */
@Serializable
public data class ProcessModel(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    val model: String? = null,

    /** Size of the model on disk. */
    @SerialName(value = "size")
    val propertySize: Long? = null,

    /** The model's digest. */
    @SerialName(value = "digest")
    val digest: String? = null,

    @SerialName(value = "details")
    val details: ModelDetails? = null,

    @SerialName(value = "expires_at")
    val expiresAt: Instant? = null,

    /** Size of the model on disk. */
    @SerialName(value = "size_vram")
    val sizeVram: Long? = null,
)
