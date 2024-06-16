package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for the show model info.
 */
@Serializable
public data class ModelInfoRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "name")
    @Required
    val name: String,
)

/**
 * A request to show the model info.
 */
public fun modelInfoRequest(block: ModelInfoRequestBuilder.() -> Unit): ModelInfoRequest =
    ModelInfoRequestBuilder().apply(block).build()

/**
 * Builder of [ModelInfoRequest] instances.
 */
@OllamaDsl
public class ModelInfoRequestBuilder {
    public var name: String? = null

    public fun build(): ModelInfoRequest = ModelInfoRequest(
        name = requireNotNull(name) { "name is required" },
    )
}
