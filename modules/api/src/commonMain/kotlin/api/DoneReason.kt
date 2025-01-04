package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.api.DoneReason.values

/**
 * Reason why the model is done generating a response.
 */
@Serializable
public enum class DoneReason(public val value: String) {

    @SerialName(value = "stop")
    STOP("stop"),

    @SerialName(value = "length")
    LENGTH("length"),

    @SerialName(value = "load")
    LOAD("load");

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
         * Returns a valid [DoneReason] for [data], null otherwise.
         */
        public fun fromValueOrNull(data: String?): DoneReason? = data?.let { value -> values().firstOrNull { it.value == value } }
    }
}
