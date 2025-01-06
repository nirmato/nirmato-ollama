package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for the show model info.
 */
@Serializable
public data class ShowModelRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "name")
    @Required
    val name: String,
) {
    public companion object {
        /** A request to show the model info. */
        public fun showModelRequest(block: ShowModelRequestBuilder.() -> Unit): ShowModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return ShowModelRequestBuilder().apply(block).build()
        }
    }

    /** Builder of [ShowModelRequest] instances. */
    @OllamaDsl
    public class ShowModelRequestBuilder {
        public var name: String? = null

        /** Create [ShowModelRequest] instance. */
        public fun build(): ShowModelRequest = ShowModelRequest(
            name = requireNotNull(name) { "name is required" },
        )
    }
}
