package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Create model request object.
 */
@Serializable
public data class CreateModelRequest(

    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "name")
    @Required
    val name: String,

    /** The contents of the Modelfile. */
    @SerialName(value = "modelfile")
    @Required
    val modelfile: String? = null,

    /** If `false` the response will be returned as a single response object, otherwise the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,

    /** Path to the Modelfile */
    @SerialName(value = "path")
    val path: String? = null,
)

/**
 * A request for creating a model.
 */
public fun createModelRequest(block: CreateModelRequestBuilder.() -> Unit): CreateModelRequest =
    CreateModelRequestBuilder().apply(block).build()

/**
 * Builder of [CreateModelRequest] instances.
 */
@OllamaDsl
public class CreateModelRequestBuilder {
    public var name: String? = null
    public var modelfile: String? = null
    public var stream: Boolean? = false
    public var path: String? = null

    public fun build(): CreateModelRequest = CreateModelRequest(
        name = requireNotNull(name) { "name is required" },
        modelfile = modelfile,
        stream = stream,
        path = path,
    )
}
