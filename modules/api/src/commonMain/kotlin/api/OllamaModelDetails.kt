package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details about a model.
 */
@Serializable
public data class OllamaModelDetails(
    @SerialName(value = "parent_model")
    val parentModel: String,

    /** The format of the model. */
    @SerialName(value = "format")
    val format: String? = null,

    /** The family of the model. */
    @SerialName(value = "family")
    val family: String? = null,

    /** The families of the model. */
    @SerialName(value = "families")
    val families: List<String>? = null,

    /** The size of the model's parameters. */
    @SerialName(value = "parameter_size")
    val parameterSize: String? = null,

    /** The quantization level of the model. */
    @SerialName(value = "quantization_level")
    val quantizationLevel: String? = null,
)
