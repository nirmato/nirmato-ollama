package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A message in the chat.
 */
@Serializable
public data class Message(
    /** The role of the message. */
    @SerialName(value = "role")
    @Required
    val role: Role,

    /** The content of the message. */
    @SerialName(value = "content")
    @Required
    val content: String,

    /** A list of Base64-encoded images to include in the message (for multimodal models such as llava). */
    @SerialName(value = "images")
    val images: List<String>? = null,

    /** A list of tools the model wants to use. */
    @SerialName(value = "tool_calls")
    val tools: List<ToolCall>? = null,
)
