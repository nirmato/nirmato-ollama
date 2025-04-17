package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

/**
 * Generate embeddings from a model.
 */
@Serializable
public data class EmbedRequest(

    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    val model: String,

    /** Text or list of text to generate embeddings for. */
    @SerialName(value = "input")
    @Required
    val input: EmbeddedInput,

    /** Truncates the end of each input to fit within context length. Returns error if false and context length is exceeded. Defaults to true. */
    @SerialName(value = "truncate")
    val truncate: Boolean? = false,

    @SerialName(value = "options")
    val options: Options? = null,

    /**
     * How long (in minutes) to keep the model loaded in memory.
     * - If set to a positive duration (e.g. 20), the model will stay loaded for the provided duration.
     * - If set to a negative duration (e.g. -1), the model will stay loaded indefinitely.
     * - If set to 0, the model will be unloaded immediately once finished.
     * - If not set, the model will stay loaded for 5 minutes by default
     */
    @SerialName(value = "keep_alive")
    val keepAlive: Int? = null,
) {
    init {
        require(model.isNotBlank()) { "Model name cannot be blank" }
    }

    public companion object {
        /** A request to generate an embeddings from a model. */
        public fun embedRequest(block: EmbedRequestBuilder.() -> Unit): EmbedRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return EmbedRequestBuilder().apply(block).build()
        }

        public fun builder(): EmbedRequestBuilder = EmbedRequestBuilder()
    }

    /** Builder of [EmbedRequest] instances. */
    @OllamaDslMarker
    public class EmbedRequestBuilder() {
        private var model: String? = null
        private var input: EmbeddedInput? = null
        private var truncate: Boolean? = null
        private var options: Options? = null
        private var keepAlive: Int? = null

        public fun model(model: String): EmbedRequestBuilder = apply { this.model = model }
        public fun input(input: EmbeddedInput): EmbedRequestBuilder = apply { this.input = input }
        public fun truncate(truncate: Boolean): EmbedRequestBuilder = apply { this.truncate = truncate }
        public fun options(options: Options): EmbedRequestBuilder = apply { this.options = options }
        public fun keepAlive(keepAlive: Int): EmbedRequestBuilder = apply { this.keepAlive = keepAlive }

        /** Create [EmbedRequest] instance. */
        public fun build(): EmbedRequest = EmbedRequest(
            model = requireNotNull(model) { "model is required" },
            input = requireNotNull(input) { "input is required" },
            truncate = truncate,
            options = options,
            keepAlive = keepAlive,
        )
    }
}
