package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ToolCall(
    @SerialName(value = "function")
    public val function: FunctionCall,
)
