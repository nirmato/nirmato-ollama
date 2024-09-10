package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A message in the chat endpoint
 */
@Serializable
public data class MessageResponse(
    /** The role of the message */
    @SerialName(value = "role")
    @Required
    val role: Role,

    /** The content of the message */
    @SerialName(value = "content")
    @Required
    val content: String,

    /** A list of Base64-encoded images to include in the message (for multimodal models such as llava) */
    @SerialName(value = "images")
    val images: List<String>? = null,

    /** A list of tools the model wants to use */
    @SerialName(value = "tool_calls")
    val toolCalls: List<ToolFunction>? = null,
) {
    /**
     * The role of the message
     */
    @Serializable
    public enum class Role(public val value: String) {
        @SerialName(value = "system")
        SYSTEM("system"),

        @SerialName(value = "user")
        USER("user"),

        @SerialName(value = "assistant")
        ASSISTANT("assistant"),

        @SerialName(value = "tool")
        TOOL("tool");
    }

    @Serializable
    public class ToolFunction(
        @SerialName(value = "name")
        public val name: String,

        @SerialName(value = "arguments")
        public val arguments: Map<String, String>? = null,
    )
}
