package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A tool call in a message.
 */
@Serializable
public data class ToolCall(
    @SerialName(value = "function")
    public val function: FunctionCall,
) {
    /**
     * Function details for a tool call.
     */
    @Serializable
    public data class FunctionCall(
        @SerialName(value = "name")
        public val name: String,

        @SerialName(value = "arguments")
        public val arguments: Map<String, String>,
    )
}
