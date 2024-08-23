package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Status creating the model.
 */
@Serializable
public enum class CreateModelStatus(public val value: String) {

    @SerialName(value = "creating system layer")
    CREATING_SYSTEM_LAYER("creating system layer"),

    @SerialName(value = "parsing modelfile")
    PARSING_MODELFILE("parsing modelfile"),

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
         * Returns a valid [CreateModelStatus] for [data], null otherwise.
         */
        public fun fromValue(data: String?): CreateModelStatus? = data?.let { value -> values().firstOrNull { it.value == value } }
    }
}
