package org.nirmato.ollama.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Returns the embedding information.
 */
@Serializable
public data class EmbedResponse(

    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    val model: String? = null,

    /** The embedding for the prompt. */
    @SerialName(value = "embeddings")
    val embeddings: List<List<Double>>? = null,

    /** Time spent generating the response. */
    @SerialName(value = "total_duration")
    val totalDuration: Long? = null,

    /** Time spent in nanoseconds loading the model. */
    @SerialName(value = "load_duration")
    val loadDuration: Long? = null,

    /** Number of tokens in the prompt. */
    @SerialName(value = "prompt_eval_count")
    val promptEvalCount: Int? = null,
)
