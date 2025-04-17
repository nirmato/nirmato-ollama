package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response class for pulling a model.
 * The first object is the manifest. Then there is a series of downloading responses. Until any of the download is completed, the `completed` key may not be included.
 * The number of files to be downloaded depends on the number of layers specified in the manifest.
 */
@Serializable
public data class ProgressResponse(

    @SerialName(value = "status")
    val status: String? = null,

    /** The model's digest. */
    @SerialName(value = "digest")
    val digest: String? = null,

    /** Total size of the model. */
    @SerialName(value = "total")
    val total: Long? = null,

    /** Total bytes transferred. */
    @SerialName(value = "completed")
    val completed: Long? = null,
)
