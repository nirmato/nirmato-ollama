package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for pulling a model.
 */
@Serializable
public data class PullModelRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    val name: String,

    /** Allow insecure connections to the library.  Only use this if you are pulling from your own library during development.  */
    @SerialName(value = "insecure")
    val insecure: Boolean? = false,

    /** If `false` the response will be returned as a single response object, otherwise the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,

    /** Ollama username. */
    @SerialName(value = "username")
    val username: String? = null,

    /** Ollama password. */
    @SerialName(value = "password")
    val password: String? = null,
)

/**
 * A request for creating a model.
 */
public fun pullModelRequest(block: PullModelRequestBuilder.() -> Unit): PullModelRequest =
    PullModelRequestBuilder().apply(block).build()

/**
 * Builder of [PullModelRequest] instances.
 */
@OllamaDsl
public class PullModelRequestBuilder {
    public var name: String? = null
    public var insecure: Boolean? = false
    public var username: String? = null
    public var password: String? = null
    public var stream: Boolean? = false

    public fun build(): PullModelRequest = PullModelRequest(
        name = requireNotNull(name) { "model is required" },
        insecure = insecure,
        username = username,
        password = password,
        stream = stream,
    )
}
