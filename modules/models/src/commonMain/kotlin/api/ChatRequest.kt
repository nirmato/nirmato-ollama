package org.nirmato.ollama.api

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDslMarker

/**
 * Request to generate a predicted chat completion for a prompt.
 */
@Serializable
public class ChatRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    @Required
    public val model: String,

    /** The messages of the chat, this can be used to keep a chat memory */
    @SerialName(value = "messages")
    @Required
    public val messages: List<Message>,

    /** The for the model to use if supported. Requires stream to be set to false. */
    @SerialName(value = "tools")
    public val tools: List<Tool>? = null,

    /** The format to return a response in. Currently, the only accepted value is json. */
    @SerialName(value = "format")
    public val format: Format? = null,

    /** Additional model parameters listed in the documentation for the Modelfile such as `temperature`. */
    @SerialName(value = "options")
    public val options: Options? = null,

    /**
     * How long (in minutes) to keep the model loaded in memory.
     *
     * - If set to a positive duration (e.g. 20), the model will stay loaded for the provided duration.
     * - If set to a negative duration (e.g. -1), the model will stay loaded indefinitely.
     * - If set to 0, the model will be unloaded immediately once finished.
     * - If not set, the model will stay loaded for 5 minutes by default
     */
    @SerialName(value = "keep_alive")
    public val keepAlive: Int? = null,

    /** If `false` the response will be returned as a single response object, otherwise, the response will be streamed as a series of objects.  */
    @SerialName(value = "stream")
    public val stream: Boolean? = false,
) {
    public companion object {
        /** A request to generate a predicted completion for a prompt. */
        public fun chatRequest(block: ChatRequestBuilder.() -> Unit): ChatRequest {
            contract {
                callsInPlace(block, InvocationKind.EXACTLY_ONCE)
            }

            return ChatRequestBuilder().apply(block).build()
        }

        public fun builder(): ChatRequestBuilder = ChatRequestBuilder()
        public fun builder(chatRequest: ChatRequest): ChatRequestBuilder = ChatRequestBuilder(chatRequest)
    }

    /** Builder of [ChatRequest] instances. */
    @OllamaDslMarker
    public class ChatRequestBuilder() {
        private var model: String? = null
        private var messages: List<Message>? = null
        private var tools: List<Tool>? = null
        private var format: Format? = null
        private var options: Options? = null
        private var keepAlive: Int? = null
        private var stream: Boolean? = false

        public constructor(chatRequest: ChatRequest) : this() {
            model = chatRequest.model
            messages = chatRequest.messages
            tools = chatRequest.tools
            format = chatRequest.format
            options = chatRequest.options
            keepAlive = chatRequest.keepAlive
            stream = chatRequest.stream
        }

        public fun model(model: String): ChatRequestBuilder = apply { this.model = model }
        public fun messages(messages: List<Message>): ChatRequestBuilder = apply { this.messages = messages }
        public fun tools(tools: List<Tool>): ChatRequestBuilder = apply { this.tools = tools }
        public fun format(format: Format): ChatRequestBuilder = apply { this.format = format }
        public fun options(options: Options): ChatRequestBuilder = apply { this.options = options }
        public fun keepAlive(keepAlive: Int): ChatRequestBuilder = apply { this.keepAlive = keepAlive }
        public fun stream(stream: Boolean): ChatRequestBuilder = apply { this.stream = stream }

        /** Create [ChatRequest] instance. */
        public fun build(): ChatRequest = ChatRequest(
            model = requireNotNull(model) { "model is required" },
            messages = requireNotNull(messages) { "messages is required" },
            tools = tools,
            format = format,
            options = options,
            keepAlive = keepAlive,
            stream = stream,
        )
    }
}
