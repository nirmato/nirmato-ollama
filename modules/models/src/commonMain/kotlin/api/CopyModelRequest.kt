package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for copying a model.
 */
@Serializable
public data class CopyModelRequest(

    /** Name of the model to copy. */
    @SerialName(value = "source")
    @Required
    val source: String,

    /** Name of the new model. */
    @SerialName(value = "destination")
    @Required
    val destination: String,
) {
    public companion object {
        /** A request for copying a model. */
        public fun copyModelRequest(block: CopyModelRequestBuilder.() -> Unit): CopyModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return CopyModelRequestBuilder().apply(block).build()
        }

        public fun builder(): CopyModelRequestBuilder = CopyModelRequestBuilder()
    }

    /** Builder of [CopyModelRequest] instances. */
    @OllamaDsl
    public class CopyModelRequestBuilder {
        public var source: String? = null
        public var destination: String? = null

        /** Create [CopyModelRequest] instance. */
        public fun build(): CopyModelRequest = CopyModelRequest(
            source = requireNotNull(source) { "source is required" },
            destination = requireNotNull(destination) { "destination is required" },
        )
    }

}
