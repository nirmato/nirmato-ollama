package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details about a model including model file, template, parameters, license, and system prompt.
 */
@Serializable
public data class ShowModelResponse(

    /** The model's license. */
    @SerialName(value = "license")
    val license: String? = null,

    /** The model file associated with the model. */
    @SerialName(value = "modelfile")
    val modelFile: String? = null,

    /** The model parameters. */
    @SerialName(value = "parameters")
    val parameters: String? = null,

    /** The prompt template for the model. */
    @SerialName(value = "template")
    val template: String? = null,

    @SerialName(value = "details")
    val details: ModelDetails? = null,
)
