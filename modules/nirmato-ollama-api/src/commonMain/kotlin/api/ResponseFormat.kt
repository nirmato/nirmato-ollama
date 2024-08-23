package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The format to return a response in.
 *
 * Enable JSON mode by setting the format parameter to json. This will structure the response as valid JSON.
 * Note: it's important to instruct the model to use JSON in the prompt. Otherwise, the model may generate large amounts whitespace.
 */
@Serializable
public enum class ResponseFormat(public val value: String) {

    @SerialName(value = "json")
    JSON("json");

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
         * Returns a valid [ResponseFormat] for [data], null otherwise.
         */
        public fun fromValue(data: String?): ResponseFormat? = data?.let { value -> values().firstOrNull { it.value == value } }
    }
}
