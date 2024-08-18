package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
