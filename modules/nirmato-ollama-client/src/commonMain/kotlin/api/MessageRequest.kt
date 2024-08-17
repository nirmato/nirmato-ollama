package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A message in the chat endpoint
 */
@Serializable
public data class MessageRequest(
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
    @SerialName(value = "tools")
    val tools: List<ToolSpecification>? = null,
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
    public class ToolSpecification(
        @SerialName(value = "type")
        public val type: String,

        @SerialName(value = "function")
        public val function: PromptFunctionSpecification,
    ) {

        @Serializable
        public class PromptFunctionSpecification(
            @SerialName(value = "name")
            public val name: String,

            @SerialName(value = "description")
            public val description: String,

            @SerialName(value = "parameters")
            public val parameters: Parameters? = null,
        )

        @Serializable
        public class Parameters(
            @SerialName(value = "type")
            public val type: String? = null,

            @SerialName(value = "required")
            public val required: List<String>? = null,

            @SerialName(value = "properties")
            public val properties: Map<String, Property>? = null,
        )

        @Serializable
        public class Property (
            @SerialName(value = "type")
            public val type: String? = null,

            @SerialName(value = "description")
            public val description: String? = null,

            @SerialName(value = "enum")
            public val enumValues: List<String>? = null,

            @SerialName(value = "required")
            public val required: Boolean? = null,
        )
    }
}
