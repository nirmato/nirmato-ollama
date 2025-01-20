package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for pushing a model.
 */
@Serializable
public data class PushModelRequest(

    /** The name of the model to push in the form of <namespace>/<model>:<tag>. */
    @SerialName(value = "model")
    @Required
    val model: String,

    /** Allow insecure connections to the library. Only use this if you are pushing to your library during development.  */
    @SerialName(value = "insecure")
    val insecure: Boolean? = false,

    /** Ollama username. */
    @SerialName(value = "username")
    val username: String? = null,

    /** Ollama password. */
    @SerialName(value = "password")
    val password: String? = null,
) {
    public companion object {
        /** A request for pushing a model. */
        public fun pushModelRequest(block: PushModelRequestBuilder.() -> Unit): PushModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return PushModelRequestBuilder().apply(block).build()
        }
    }

    /** Builder of [PushModelRequest] instances. */
    @OllamaDsl
    public class PushModelRequestBuilder {
        public var model: String? = null
        public var insecure: Boolean? = false
        public var username: String? = null
        public var password: String? = null

        /** Create [PushModelRequest] instance. */
        public fun build(): PushModelRequest = PushModelRequest(
            model = requireNotNull(model) { "model is required" },
            insecure = insecure,
            username = username,
            password = password,
        )
    }
}
