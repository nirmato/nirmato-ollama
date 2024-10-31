package org.nirmato.ollama.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.nirmato.ollama.dsl.OllamaDsl

/**
 * Request class for the show model info.
 */
@Serializable
public data class ShowModelInformationRequest(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "name")
    @Required
    val name: String,
) {
    public companion object {
        /** A request to show the model info. */
        public fun showModelInformationRequest(block: ShowModelInformationRequestBuilder.() -> Unit): ShowModelInformationRequest =
            ShowModelInformationRequestBuilder().apply(block).build()
    }

    /** Builder of [ShowModelInformationRequest] instances. */
    @OllamaDsl
    public class ShowModelInformationRequestBuilder {
        public var name: String? = null

        /** Create [ShowModelInformationRequest] instance. */
        public fun build(): ShowModelInformationRequest = ShowModelInformationRequest(
            name = requireNotNull(name) { "name is required" },
        )
    }
}
