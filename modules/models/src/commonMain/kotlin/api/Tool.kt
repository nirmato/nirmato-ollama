package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Tool(
    @SerialName(value = "type")
    public val type: String,

    @SerialName(value = "function")
    public val function: ToolFunction,
) {

    @Serializable
    public data class ToolFunction(
        @SerialName(value = "name")
        public val name: String,

        @SerialName(value = "description")
        public val description: String,

        @SerialName(value = "parameters")
        public val parameters: Parameters? = null,
    )

    @Serializable
    public data class Parameters(
        @SerialName(value = "type")
        public val type: String? = null,

        @SerialName(value = "required")
        public val required: List<String>? = null,

        @SerialName(value = "properties")
        public val properties: Map<String, Property>? = null,
    )

    @Serializable
    public data class Property(
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
