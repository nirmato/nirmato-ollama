package org.nirmato.ollama.api

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A model available locally.
 */
@Serializable
public data class OllamaModel(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "name")
    val name: String? = null,

    @SerialName(value = "model")
    val model: String? = null,

    /** Model modification date. */
    @SerialName(value = "modified_at")
    val modifiedAt: Instant? = null,

    /** Model expiration date. */
    @SerialName(value = "expires_at")
    val expiresAt: Instant? = null,

    /** Size of the model on disk. */
    @SerialName(value = "size")
    val size: Long? = null,

    /** The model's digest. */
    @SerialName(value = "digest")
    val digest: String? = null,

    @SerialName(value = "details")
    val details: ModelDetails? = null,

    /** The model's digest. */
    @SerialName(value = "size_vram")
    val sizeVram: Long? = null,
)
