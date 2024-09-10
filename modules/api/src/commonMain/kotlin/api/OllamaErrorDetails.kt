package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error response from the Ollama API.
 */
@Serializable
public data class OllamaError(
    /** Information about the error that occurred. */
    @SerialName("error")
    public val detail: OllamaErrorDetails? = null,
)

/**
 * Represents an error object returned by the Ollama API.
 */
@Serializable
public data class OllamaErrorDetails(
    /** An error code identifying the error type. */
    @SerialName("code")
    val code: String? = null,
    /** A human-readable message providing more details about the error. */
    @SerialName("message")
    val message: String? = null,
    /** The name of the parameter that caused the error, if applicable. */
    @SerialName("param")
    val param: String? = null,
    /** The type of error that occurred. */
    @SerialName("type")
    val type: String? = null,
    /** The line number of the input file where the error occurred, if applicable. */
    @SerialName("line")
    val line: Int? = null,
)
