package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details about a model including modelfile, template, parameters, license, and system prompt.
 */
@Serializable
public data class ModelInfo(

    /** The model's license. */
    @SerialName(value = "license")
    val license: String? = null,

    /** The modelfile associated with the model. */
    @SerialName(value = "modelfile")
    val modelfile: String? = null,

    /** The model parameters. */
    @SerialName(value = "parameters")
    val parameters: String? = null,

    /** The prompt template for the model. */
    @SerialName(value = "template")
    val template: String? = null,

    /** The system prompt for the model. */
    @SerialName(value = "system")
    val system: String? = null,

    @SerialName(value = "details")
    val details: ModelDetails? = null,

    /** The default messages for the model. */
    @SerialName(value = "messages")
    val messages: List<Message>? = null,
)
