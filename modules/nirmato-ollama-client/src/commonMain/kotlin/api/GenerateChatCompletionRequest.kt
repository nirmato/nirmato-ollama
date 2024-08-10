package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request to generate a predicted chat completion for a prompt.
 */
@Serializable
public data class GenerateChatCompletionRequest(

    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    val model: String,

    /** The messages of the chat, this can be used to keep a chat memory */
    @SerialName(value = "messages")
    @Required
    val messages: List<Message>,

    /** The format to return a response in. Currently, the only accepted value is json. */
    @SerialName(value = "format")
    val format: ResponseFormat? = null,

    /** Additional model parameters listed in the documentation for the Modelfile such as `temperature`. */
    @SerialName(value = "options")
    val options: RequestOptions? = null,

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    val stream: Boolean? = false,

    /**
     * How long (in minutes) to keep the model loaded in memory.
     *
     * - If set to a positive duration (e.g. 20), the model will stay loaded for the provided duration.
     * - If set to a negative duration (e.g. -1), the model will stay loaded indefinitely.
     * - If set to 0, the model will be unloaded immediately once finished.
     * - If not set, the model will stay loaded for 5 minutes by default
     */
    @SerialName(value = "keep_alive")
    val keepAlive: Int? = null,
) {
    public companion object {
        /** A request to generate a predicted completion for a prompt. */
        public fun generateChatCompletionRequest(block: GenerateChatCompletionRequestBuilder.() -> Unit): GenerateChatCompletionRequest =
            GenerateChatCompletionRequestBuilder().apply(block).build()
    }

    /** Builder of [GenerateChatCompletionRequest] instances. */
    @OllamaDsl
    public class GenerateChatCompletionRequestBuilder {
        public var model: String? = null
        public var messages: List<Message>? = null
        public var format: ResponseFormat? = null
        public var options: RequestOptions? = null
        public var stream: Boolean? = false
        public var keepAlive: Int? = null

        /** Create [GenerateChatCompletionRequest] instance. */
        public fun build(): GenerateChatCompletionRequest = GenerateChatCompletionRequest(
            model = requireNotNull(model) { "model is required" },
            messages = requireNotNull(messages) { "messages is required" },
            format = format,
            options = options,
            stream = stream,
            keepAlive = keepAlive,
        )
    }
}
