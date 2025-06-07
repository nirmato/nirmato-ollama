package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

/**
 * Request class for deleting a model.
 */
@Serializable
public data class DeleteModelRequest(

    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    val model: String,
) {
    public companion object {
        /** A request for creating a model. */
        public fun deleteModelRequest(block: DeleteModelRequestBuilder.() -> Unit): DeleteModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return DeleteModelRequestBuilder().apply(block).build()
        }

        public fun builder(): DeleteModelRequestBuilder = DeleteModelRequestBuilder()
    }

    /** Builder of [DeleteModelRequest] instances. */
    @OllamaDslMarker
    public class DeleteModelRequestBuilder() {
        public var model: String? = null

        public fun model(model: String): DeleteModelRequestBuilder = apply { this.model = model }

        /** Create [DeleteModelRequest] instance. */
        public fun build(): DeleteModelRequest = DeleteModelRequest(
            model = requireNotNull(model) { "model is required" },
        )
    }
}
