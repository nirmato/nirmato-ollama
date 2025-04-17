package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

/**
 * Request for pushing a model.
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

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,
) {
    init {
        require(model.isNotBlank()) { "Model name cannot be blank" }
    }

    public companion object {
        /** A request for pushing a model. */
        public fun pushModelRequest(block: PushModelRequestBuilder.() -> Unit): PushModelRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return PushModelRequestBuilder().apply(block).build()
        }

        public fun builder(): PushModelRequestBuilder = PushModelRequestBuilder()
        public fun builder(pushModelRequest: PushModelRequest): PushModelRequestBuilder = PushModelRequestBuilder(pushModelRequest)
    }

    /** Builder of [PushModelRequest] instances. */
    @OllamaDslMarker
    public class PushModelRequestBuilder() {
        private var model: String? = null
        private var insecure: Boolean? = false
        private var username: String? = null
        private var password: String? = null
        private var stream: Boolean? = null

        public constructor(pushModelRequest: PushModelRequest) : this() {
            model = pushModelRequest.model
            insecure = pushModelRequest.insecure
            username = pushModelRequest.username
            password = pushModelRequest.password
            stream = pushModelRequest.stream
        }

        public fun model(model: String): PushModelRequestBuilder = apply { this.model = model }
        public fun insecure(insecure: Boolean): PushModelRequestBuilder = apply { this.insecure = insecure }
        public fun username(username: String): PushModelRequestBuilder = apply { this.username = username }
        public fun password(password: String): PushModelRequestBuilder = apply { this.password = password }
        public fun stream(stream: Boolean): PushModelRequestBuilder = apply { this.stream = stream }

        /** Create [PushModelRequest] instance. */
        public fun build(): PushModelRequest = PushModelRequest(
            model = requireNotNull(model) { "model is required" },
            insecure = insecure,
            username = username,
            password = password,
            stream = stream,
        )
    }
}
