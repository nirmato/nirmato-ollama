package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

/**
 * Request to generate a predicted completion for a prompt.
 */
@Serializable
public data class ChatCompletionRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    val model: String,

    /** The prompt to generate a response. */
    @SerialName(value = "prompt")
    @Required
    val prompt: String,

    /** The text after the model response. */
    @SerialName(value = "suffix")
    val suffix: String? = null,

    /** A list of Base64-encoded images to include in the message (for multimodal models such as llava) */
    @SerialName(value = "images")
    val images: List<String>? = null,

    /** The format to return a response in. Format can be json or a JSON schema */
    @SerialName(value = "format")
    val format: Format? = null,

    /** Additional model parameters listed in the documentation for the Modelfile such as `temperature`. */
    @SerialName(value = "options")
    val options: Options? = null,

    /** The system prompt to (overrides what is defined in the Modelfile). */
    @SerialName(value = "system")
    val system: String? = null,

    /** The full prompt or prompt template to use (overrides what is defined in the Modelfile). */
    @SerialName(value = "template")
    val template: String? = null,

    /**
     * If `true` no formatting will be applied to the prompt and no context will be returned.
     * You may choose to use the `raw` parameter if you are specifying a full templated prompt in your request to the API, and are managing history yourself.
     */
    @SerialName(value = "raw")
    val raw: Boolean? = null,

    /**
     * How long (in minutes) to keep the model loaded into memory following the request (default: 5m)
     *
     * - If set to a positive duration (e.g. 20), the model will stay loaded for the provided duration.
     * - If set to a negative duration (e.g. -1), the model will stay loaded indefinitely.
     * - If set to 0, the model will be unloaded immediately once finished.
     * - If not set, the model will stay loaded for 5 minutes by default
     */
    @SerialName(value = "keep_alive")
    val keepAlive: Int? = null,

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,

    /** The context parameter returned from a previous request to [CompletionsApi#completion], this can be used to keep a short conversational memory. */
    @SerialName(value = "context")
    val context: List<Long>? = null,
) {
    init {
        require(model.isNotBlank()) { "Model name cannot be blank" }
    }

    public companion object {
        /** A request to generate a predicted completion for a prompt. */
        public fun chatCompletionRequest(block: ChatCompletionRequestBuilder.() -> Unit): ChatCompletionRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return ChatCompletionRequestBuilder().apply(block).build()
        }

        public fun builder(): ChatCompletionRequestBuilder = ChatCompletionRequestBuilder()
        public fun builder(chatCompletionRequest: ChatCompletionRequest): ChatCompletionRequestBuilder = ChatCompletionRequestBuilder(chatCompletionRequest)
    }

    /** Builder of [ChatCompletionRequest] instances. */
    @OllamaDslMarker
    public class ChatCompletionRequestBuilder() {
        private var model: String? = null
        private var prompt: String? = null
        private var suffix: String? = null
        private var images: List<String>? = null
        private var format: Format? = null
        private var options: Options? = null
        private var system: String? = null
        private var template: String? = null
        private var context: List<Long>? = null
        private var raw: Boolean? = null
        private var keepAlive: Int? = null
        private var stream: Boolean? = null

        public constructor(chatCompletionRequest: ChatCompletionRequest) : this() {
            model = chatCompletionRequest.model
            prompt = chatCompletionRequest.prompt
            suffix = chatCompletionRequest.suffix
            images = chatCompletionRequest.images
            format = chatCompletionRequest.format
            options = chatCompletionRequest.options
            system = chatCompletionRequest.system
            template = chatCompletionRequest.template
            context = chatCompletionRequest.context
            raw = chatCompletionRequest.raw
            keepAlive = chatCompletionRequest.keepAlive
            stream = chatCompletionRequest.stream
        }

        public fun model(model: String): ChatCompletionRequestBuilder = apply { this.model = model }
        public fun prompt(prompt: String): ChatCompletionRequestBuilder = apply { this.prompt = prompt }
        public fun suffix(suffix: String): ChatCompletionRequestBuilder = apply { this.suffix = suffix }
        public fun images(images: List<String>): ChatCompletionRequestBuilder = apply { this.images = images }
        public fun format(format: Format): ChatCompletionRequestBuilder = apply { this.format = format }
        public fun options(options: Options): ChatCompletionRequestBuilder = apply { this.options = options }
        public fun system(system: String): ChatCompletionRequestBuilder = apply { this.system = system }
        public fun template(template: String): ChatCompletionRequestBuilder = apply { this.template = template }
        public fun context(context: List<Long>): ChatCompletionRequestBuilder = apply { this.context = context }
        public fun raw(raw: Boolean): ChatCompletionRequestBuilder = apply { this.raw = raw }
        public fun keepAlive(keepAlive: Int): ChatCompletionRequestBuilder = apply { this.keepAlive = keepAlive }
        public fun stream(stream: Boolean): ChatCompletionRequestBuilder = apply { this.stream = stream }

        /** Create [ChatCompletionRequest] instance. */
        public fun build(): ChatCompletionRequest = ChatCompletionRequest(
            model = requireNotNull(model) { "model is required" },
            prompt = requireNotNull(prompt) { "prompt is required" },
            suffix = suffix,
            images = images,
            format = format,
            options = options,
            system = system,
            template = template,
            context = context,
            raw = raw,
            keepAlive = keepAlive,
            stream = stream,
        )
    }
}
