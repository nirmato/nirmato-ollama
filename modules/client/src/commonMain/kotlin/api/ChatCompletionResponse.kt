package org.nirmato.ollama.api

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The response class for the generate endpoint.
 */
@Serializable
public data class ChatCompletionResponse(
    /**
     * The model name.
     * Model names follow a model:tag format, where model can have an optional namespace such as example/model.
     * Some examples are orca-mini:3b-q4_1 and llama3:70b. The tag is optional and, if not provided, will default to latest.
     * The tag is used to identify a specific version.
     */
    @SerialName(value = "model")
    val model: String? = null,

    /** Date on which a model was created. */
    @SerialName(value = "created_at")
    val createdAt: Instant? = null,

    /**
     * The response for a given prompt with a provided model.
     *
     * Empty if the response was streamed, if not streamed, this will contain the full response
     */
    @SerialName(value = "response")
    val response: String? = null,

    /** Whether the response has completed. */
    @SerialName(value = "done")
    val done: Boolean? = null,

    /** Whether the response has completed. */
    @SerialName(value = "done_reason")
    val doneReason: DoneReason? = null,

    /** Time spent generating the response. */
    @SerialName(value = "total_duration")
    val totalDuration: Long? = null,

    /** Time spent in nanoseconds loading the model. */
    @SerialName(value = "load_duration")
    val loadDuration: Long? = null,

    /** Number of tokens in the prompt. */
    @SerialName(value = "prompt_eval_count")
    val promptEvalCount: Int? = null,

    /** Time spent in nanoseconds evaluating the prompt. */
    @SerialName(value = "prompt_eval_duration")
    val promptEvalDuration: Long? = null,

    /** Number of tokens the response. */
    @SerialName(value = "eval_count")
    val evalCount: Int? = null,

    /** Time in nanoseconds spent generating the response. */
    @SerialName(value = "eval_duration")
    val evalDuration: Long? = null,

    /** An encoding of the conversation used in this response, this can be sent in the next request to keep a conversational memory. */
    @SerialName(value = "context")
    val context: List<Long>? = null,
)
