package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Status pulling the model.
 */
@Serializable
public enum class PullModelStatus(public val value: String) {

    @SerialName(value = "pulling manifest")
    PULLING_MANIFEST("pulling manifest"),

    @SerialName(value = "downloading digestname")
    DOWNLOADING_DIGESTNAME("downloading digestname"),

    @SerialName(value = "verifying sha256 digest")
    VERIFYING_SHA256_DIGEST("verifying sha256 digest"),

    @SerialName(value = "writing manifest")
    WRITING_MANIFEST("writing manifest"),

    @SerialName(value = "removing any unused layers")
    REMOVING_ANY_UNUSED_LAYERS("removing any unused layers"),

    @SerialName(value = "success")
    SUCCESS("success");

    /**
     * Override [toString()] to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): String = value

    public companion object {

        /**
         * Returns a valid [PullModelStatus] for [data], null otherwise.
         */
        public fun fromValue(data: String?): PullModelStatus? = data?.let { value -> values().firstOrNull { it.value == value } }
    }
}

