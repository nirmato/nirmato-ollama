package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class ModelCapability {
    @SerialName("completion")
    Completion,

    @SerialName("tools")
    Tools,

    @SerialName("insert")
    Insert,

    @SerialName("vision")
    Vision,

    @SerialName("embedding")
    Embedding,

    @SerialName("thinking")
    Thinking,
}
