package org.nirmato.ollama.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
public sealed interface Format {
    /**
     * The format to return a response in. Currently, the only accepted value is json.
     *
     * Enable JSON mode by setting the format parameter to json. This will structure the response as valid JSON.
     * Note: it's important to instruct the model to use JSON in the prompt. Otherwise, the model may generate large amounts whitespace.
     */
    @JvmInline
    @Serializable
    public value class FormatType(public val value: String) : Format {
        public companion object {
            public val JSON: FormatType = FormatType("json")
        }
    }

    @JvmInline
    @Serializable
    public value class FormatSchema(public val schema: JsonObject) : Format
}
