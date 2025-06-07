package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * A tool definition.
 */
@Serializable
public data class Tool(
    @SerialName(value = "type")
    public val type: String,

    @SerialName(value = "function")
    public val function: ToolFunction,
) {

    /**
     * Function details for a tool definition.
     */
    @Serializable
    public data class ToolFunction(
        @SerialName(value = "name")
        public val name: String,

        @SerialName(value = "description")
        public val description: String,

        @SerialName(value = "parameters")
        public val parameters: ToolParameters? = null,
    )

    /**
     * Parameters for a tool definition.
     */
    @Serializable
    public data class ToolParameters(
        @SerialName(value = "properties")
        public val properties: Map<String, JsonObject>? = null,

        @SerialName(value = "required")
        public val required: List<String>? = null,
    ) {
        @SerialName(value = "type")
        public val type: String = "object"
    }
}
