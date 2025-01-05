package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request to generate a predicted completion for a prompt.
 */
@Serializable
public data class CompletionRequest(
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

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects. */
    @SerialName(value = "stream")
    val stream: Boolean? = false,

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

    /** The context parameter returned from a previous request to [CompletionsApi.completion], this can be used to keep a short conversational memory. */
    @Deprecated(message = "Deprecated property")
    @SerialName(value = "context")
    val context: List<Long>? = null,
) {
    public companion object {
        /** A request to generate a predicted completion for a prompt. */
        public fun completionRequest(block: CompletionRequestBuilder.() -> Unit): CompletionRequest = CompletionRequestBuilder().apply(block).build()
    }

    /** Builder of [CompletionRequest] instances. */
    @OllamaDsl
    public class CompletionRequestBuilder {
        public var model: String? = null
        public var prompt: String? = null
        public var suffix: String? = null
        public var images: List<String>? = null
        public var format: Format? = null
        public var options: Options? = null
        public var system: String? = null
        public var template: String? = null
        public var context: List<Long>? = null
        public var stream: Boolean? = false
        public var raw: Boolean? = null
        public var keepAlive: Int? = null

        /** Create [CompletionRequest] instance. */
        public fun build(): CompletionRequest = CompletionRequest(
            model = requireNotNull(model) { "model is required" },
            prompt = requireNotNull(prompt) { "prompt is required" },
            suffix = suffix,
            images = images,
            format = format,
            options = options,
            system = system,
            template = template,
            context = context,
            stream = stream,
            raw = raw,
            keepAlive = keepAlive,
        )
    }
}
