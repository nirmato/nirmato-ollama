package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.api.PushModelStatus.values

/**
 * Status pushing the model.
 */
@Serializable
public enum class PushModelStatus(public val value: String) {

    @SerialName(value = "retrieving manifest")
    RETRIEVING_MANIFEST("retrieving manifest"),

    @SerialName(value = "starting upload")
    STARTING_UPLOAD("starting upload"),

    @SerialName(value = "pushing manifest")
    PUSHING_MANIFEST("pushing manifest"),

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
         * Returns a valid [PushModelStatus] for [data], null otherwise.
         */
        public fun fromValueOrNull(data: String?): PushModelStatus? = data?.let { value -> values().firstOrNull { it.value == value } }
    }
}
